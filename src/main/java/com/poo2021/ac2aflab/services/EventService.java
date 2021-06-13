package com.poo2021.ac2aflab.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.poo2021.ac2aflab.dto.Event.EventDTO;
import com.poo2021.ac2aflab.dto.Event.EventInsertDTO;
import com.poo2021.ac2aflab.dto.Event.EventUpdateDTO;
import com.poo2021.ac2aflab.dto.Ticket.TicketDTO;
import com.poo2021.ac2aflab.dto.Ticket.TicketSellDTO;
import com.poo2021.ac2aflab.entites.Attendee;
import com.poo2021.ac2aflab.entites.Event;
import com.poo2021.ac2aflab.entites.Place;
import com.poo2021.ac2aflab.entites.Ticket;
import com.poo2021.ac2aflab.entites.Ticket.TicketType;
import com.poo2021.ac2aflab.repositories.AdminRepository;
import com.poo2021.ac2aflab.repositories.AttendeeRepository;
import com.poo2021.ac2aflab.repositories.EventRepository;
import com.poo2021.ac2aflab.repositories.PlaceRepository;
import com.poo2021.ac2aflab.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService {
    
    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private AttendeeRepository atendeeRepo;

    @Autowired
    private PlaceRepository placeRepo;

    @Autowired
    private TicketRepository ticketRepo;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String emailContact, LocalDate startDate) {
        Page<Event> list = eventRepo.find(pageRequest, name, description, emailContact, startDate);
        return list.map( a -> new EventDTO(a));
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> op = eventRepo.findById(id);
        Event event = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return new EventDTO(event);
    }

    public EventDTO insert(EventInsertDTO insertDTO) {
        
        if (insertDTO.getStartDate().compareTo(insertDTO.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The end date should be bigger than the start date!");
        }
        if (insertDTO.getStartDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Cannot insert events in the past!");
        }

        Event entity = new Event(insertDTO);
        Place place;

        try{
            entity.setAdmin(adminRepo.findById(insertDTO.getAdminId()).get());
        }catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }

        try{
            place = placeRepo.findById(insertDTO.getPlaceId()).get();
        }catch (NoSuchElementException a) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }

        entity.getPlaces().add(place);
        
        if(!isDateTimeValid(entity))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Confilicting date time!");

        if(insertDTO.getAmountFreeTickets() > 0) {
            for (int i = 0 ; i < insertDTO.getAmountFreeTickets(); i++) {
                Ticket ticket = new Ticket();
                ticket.setEvent(entity);
                ticket.setType(TicketType.FREE);
                ticket.setDate(Instant.now());
                ticket.setPrice(0.0);
                entity.getTickets().add(ticket);
            }
        }

        if(insertDTO.getAmountPayedTickets() > 0) {
            for (int i = 0 ; i < insertDTO.getAmountPayedTickets(); i++) {
                Ticket payedTicket = new Ticket();
                payedTicket.setEvent(entity);
                payedTicket.setType(TicketType.PAYED);
                payedTicket.setDate(Instant.now());
                payedTicket.setPrice(entity.getPriceTicket());
                entity.getTickets().add(payedTicket);
            }
        }
        
        entity = eventRepo.save(entity);
        ticketRepo.saveAll(entity.getTickets());
        placeRepo.saveAll(entity.getPlaces());
        return new EventDTO(entity);
    }

    public EventDTO insertPlace(Long eventId, Long placeId) {
        Event entity;
        Place place;

        try{
            entity = eventRepo.findById(eventId).get();
        }catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        if(entity.getEndDate().isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't insert place on past event");

        try{
            place = placeRepo.findById(placeId).get();
        }catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
        
        if(entity.getPlaces().contains(place))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Place already assinged to this event");

        entity.getPlaces().add(place);

        if(!isDateTimeValid(entity))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Confilicting date time!");
       
        entity = eventRepo.save(entity);
        placeRepo.saveAll(entity.getPlaces());
        return new EventDTO(entity);
    }

    public TicketDTO sellTicket(TicketSellDTO sellDTO, Long eventId) {
        Event entity;
        Attendee attendee;
        try{
            entity = eventRepo.findById(eventId).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        try{
            attendee = atendeeRepo.findById(sellDTO.getAttendeeId()).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
        Ticket ticket = new Ticket();
        if(entity.getEndDate().isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cant buy tickets from past events!");
        if(entity.getPriceTicket() > attendee.getBalance())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Balance insuficient to buy this ticket!");
        
        for(Ticket t : entity.getTickets()) {
            if(t.getAttendee() == null && t.getType() == sellDTO.getType()){
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA");
                ticket = ticketRepo.findById(t.getId()).get();
                ticket.setAttendee(attendee);
                attendee.getTickets().add(ticket);
                attendee.setBalance(attendee.getBalance() - ticket.getPrice());
                break;
            }
        }
        System.out.println(ticket.getType());
        if(ticket.getAttendee() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Event sold out on this type of ticket!");
        }
        entity = eventRepo.save(entity);
        ticketRepo.save(ticket);
        atendeeRepo.save(attendee);
        return new TicketDTO(ticket);
    }


    public void delete(Long id) {
        Event event = eventRepo.findById(id).get();
        if(event.getEndDate().isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't delete a past event");

        List<Ticket> tickets = eventRepo.findById(id).get().getTickets();
        for(Ticket t : tickets) {
            if(t.getAttendee() != null)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't delete a event that already sold tickets");
        }
        try {
            eventRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public void deletePlace(long eventId, Long placeId) {
        Event event;
        Place place;

        try{
            event = eventRepo.findById(eventId).get();
        }catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }

        try{
            place = placeRepo.findById(placeId).get();
        }catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }

        if(!event.getPlaces().contains(place))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");

        event.getPlaces().remove(place);
        eventRepo.save(event);
    }

    public EventDTO update(Long id, EventUpdateDTO updateDTO) {

        if (updateDTO.getStartDate().compareTo(updateDTO.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The end date should be bigger than the start date!");
        } 
        else if (updateDTO.getEndDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update past events!");
        } else{
            try {
                Event entity = eventRepo.getOne(id);

                entity.setDescription(updateDTO.getDescription());
                entity.setStartDate(updateDTO.getStartDate());
                entity.setEndDate(updateDTO.getEndDate());
                entity.setStartTime(updateDTO.getStartTime());
                entity.setEndTime(updateDTO.getEndTime());
                entity.setAmountFreeTickets(updateDTO.getAmountFreeTickets());
                entity.setPriceTicket(updateDTO.getPriceTicket());

                if(!isDateTimeValid(entity))
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Confilicting date time!");

                for(Ticket t : entity.getTickets()){
                    if(t.getType() == TicketType.PAYED)
                        t.setPrice(entity.getPriceTicket());
                }
                try {
                    Place newPlace = placeRepo.findById(updateDTO.getNewPlaceId()).get();
                    Place oldPlace = placeRepo.findById(updateDTO.getOldPlaceId()).get();

                     if(!entity.getPlaces().contains(oldPlace))
                         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found to update to a new one");
                    if(entity.getPlaces().contains(newPlace))
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Place already assinged to this event");
                    for(Place p : entity.getPlaces()) {
                        if(p.getId() == oldPlace.getId()){
                            entity.getPlaces().remove(p);
                            entity.getPlaces().add(newPlace);
                            break;
                        }
                    }
                }catch(EntityNotFoundException e){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
                }

                entity = eventRepo.save(entity);
                ticketRepo.saveAll(entity.getTickets());
                return new EventDTO(entity);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }
        }
    }

    private boolean isDateTimeValid(Event event) {
        boolean b = true;

        //se o horario inicial for maior que o horario final e estiverem no mesmo dia 
        if(event.getStartTime().isAfter(event.getEndTime()) && event.getStartDate().compareTo(event.getEndDate()) == 0)
        {
            b = false;
            return b;
        }
        for(Event e: eventRepo.findAll()) {

            LocalTime startT = e.getStartTime();
            LocalTime endT = e.getEndTime();
            LocalDate startD = e.getStartDate();
            LocalDate endD = e.getEndDate();
            for(Place p : e.getPlaces()) {
                if(event.getPlaces().contains(p) && e.getId() != event.getId()) {

                    //se a data INICIAL inserida for IGUAL a data INICIAL ja alocada OU se a data FINAL inserida for IGUAL a data FINAL ja alocada OU 
                    //se a data INICIAL inserida for IGUAL a data FINAL ja alocada OU se a data FINAL inserida for IGUAL a data INICIAL ja alocada OU 
                    //se a data INICIAL inserida estiver DEPOIS da data FINAL ja alocada E se a data INICIAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada E se a data FINAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data INICIAL inserida estiver ANTES da data INICIAL ja alocada E se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada
                    if(event.getStartDate().compareTo(startD) == 0 || event.getEndDate().compareTo(endD) == 0 ||
                    event.getStartDate().compareTo(endD) == 0 || event.getEndDate().compareTo(startD) == 0 ||
                    event.getStartDate().isAfter(startD) &&  event.getStartDate().isBefore(endD) || 
                    event.getEndDate().isAfter(startD) && event.getEndDate().isBefore(endD) || 
                    event.getStartDate().isBefore(startD) && event.getEndDate().isAfter(endD)) {

                        //se o horario INICIAL inserido for IGUAL o horario INICIAL ja alocado OU se o horario FINAL inserido for IGUAL o horario FINAL ja alocado OU 
                        //se o horario INICIAL inserido for IGUAL o horario FINAL ja alocado OU se o horario FINAL inserido for IGUAL o horario INICIAL ja alocado OU 
                        if(event.getStartTime().compareTo(startT) == 0 || event.getEndTime().compareTo(endT) == 0  || 
                            event.getStartTime().compareTo(endT) == 0 || event.getEndTime ().compareTo(startT) == 0) {
                            b = false;
                            break;
                        }

                        //se o horario INICIAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario INICIAL inserido estiver ANTES do horario FINAL ja alocado OU
                        //se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario FINAL inserido estiver ANTES do horario FINAL ja alocado OU
                        else if(event.getStartTime().isAfter(startT) &&  event.getStartTime().isBefore(endT) || 
                                event.getEndTime().isAfter(startT) && event.getEndTime().isBefore(endT)){
                                b = false;
                                break;
                            }   

                        //se o horario INICIAL inserido estiver ANTES do horario INICIAL ja alocado E se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado
                        else if(event.getStartTime().isBefore(startT) && event.getEndTime().isAfter(endT)){
                            b = false;
                            break;
                        }
                    }
                }
            }
        }
        return b;
    }

}
