package com.poo2021.ac2aflab.services;

<<<<<<< HEAD
import java.time.LocalDate;
import java.time.LocalTime;
=======
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
>>>>>>> AF
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

<<<<<<< HEAD
import com.poo2021.ac2aflab.dto.EventDTO;
import com.poo2021.ac2aflab.dto.EventInsertDTO;
import com.poo2021.ac2aflab.dto.EventUpdateDTO;
import com.poo2021.ac2aflab.entites.Event;
import com.poo2021.ac2aflab.entites.Place;
import com.poo2021.ac2aflab.entites.Admin;
import com.poo2021.ac2aflab.repositories.AdminRepository;
import com.poo2021.ac2aflab.repositories.EventRepository;
import com.poo2021.ac2aflab.repositories.PlaceRepository;

=======
import com.poo2021.ac2aflab.dto.Event.EventDTO;
import com.poo2021.ac2aflab.dto.Event.EventInsertDTO;
import com.poo2021.ac2aflab.dto.Event.EventUpdateDTO;
import com.poo2021.ac2aflab.dto.Ticket.TicketDTO;
import com.poo2021.ac2aflab.dto.Ticket.TicketRefundDTO;
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
>>>>>>> AF

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
<<<<<<< HEAD
    private PlaceRepository placeRepo;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description) {
        Page<Event> list = eventRepo.find(pageRequest, name, description);
=======
    private AttendeeRepository attendeeRepo;

    @Autowired
    private PlaceRepository placeRepo;

    @Autowired
    private TicketRepository ticketRepo;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description, String emailContact, LocalDate startDate) {
        Page<Event> list = eventRepo.find(pageRequest, name, description, emailContact, startDate);
>>>>>>> AF
        return list.map( a -> new EventDTO(a));
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> op = eventRepo.findById(id);
<<<<<<< HEAD
        Event Event = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        return new EventDTO(Event);
    }

    public EventDTO insert(EventInsertDTO insertDTO) {
        if (insertDTO.getStartDate().compareTo(insertDTO.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The end date should be bigger than the start date!");
        } else if(!isDateTimeValid(insertDTO)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Confilicting date time!");
        } else {
            Event entity = new Event(insertDTO);
            for(Admin admin : adminRepo.findAll())
            {
                if(admin.getId() == insertDTO.getAdminId())
                    entity.setAdmin(admin);    
            }
            for(Place place : placeRepo.findAll()) {
                if(place.getId() == insertDTO.getPlaceId())
                    entity.getPlaces().add(place);
            }
            entity = eventRepo.save(entity);
            return new EventDTO(entity);
        }
    }

    public void delete(Long id) {
=======
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
        entity.setFreeTicketsSelled(0l);
        entity.setPayedTicketsSelled(0l);
        entity = eventRepo.save(entity);
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
            attendee = attendeeRepo.findById(sellDTO.getAttendeeId()).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }

        if(entity.getEndDate().isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cant buy tickets from past events!");

        if(entity.getPriceTicket() > attendee.getBalance() && sellDTO.getType() == TicketType.PAYED)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Balance insufficient to buy this ticket!");
        
        Ticket ticket = new Ticket();
        if(sellDTO.getType() == TicketType.FREE) {
            Long selledTickets = entity.getAmountFreeTicketsSelled(1l);
            if(entity.getAmountFreeTickets() < selledTickets)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Event sold out on free tickets!");
            else {
                ticket.setAttendee(attendee);
                ticket.setDate(Instant.now());
                ticket.setEvent(entity);
                ticket.setPrice(0.0);
                ticket.setType(sellDTO.getType());
                attendee.getTickets().add(ticket);
                ticketRepo.save(ticket);
            }
        } else if(sellDTO.getType() == TicketType.PAYED) {
            Long selledTickets = entity.getAmountPayedTicketsSelled(1l);
            if(entity.getAmountPayedTickets() < selledTickets)
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Event sold out on payed tickets!");
            else {
                ticket.setAttendee(attendee);
                ticket.setDate(Instant.now());
                ticket.setEvent(entity);
                ticket.setPrice(entity.getPriceTicket());
                ticket.setType(sellDTO.getType());
                attendee.getTickets().add(ticket);
                attendee.setBalance(attendee.getBalance() - entity.getPriceTicket());
                ticketRepo.save(ticket);
            }
        }
        
        entity = eventRepo.save(entity);
        attendeeRepo.save(attendee);
        
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
>>>>>>> AF
        try {
            eventRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

<<<<<<< HEAD
    public EventDTO update(Long id, EventUpdateDTO updateDTO) {

        if (updateDTO.getStartDate().compareTo(updateDTO.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "The end date should be bigger than the start date!");
        } else if (updateDTO.getStartDate().compareTo(LocalDate.now()) < 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Cannot update past events!");
        } else if(!isDateTimeValid(updateDTO)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Confilicting date time!");
=======
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

    public void refundTicket(TicketRefundDTO refundDTO, Long eventId) {
        Event entity;
        Attendee attendee;
        Ticket ticket;
        try{
            entity = eventRepo.findById(eventId).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        try{
            attendee = attendeeRepo.findById(refundDTO.getAttendeeId()).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
        try{
            ticket = ticketRepo.findById(refundDTO.getTicketId()).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }

        if(entity.getStartDate().isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cant refund tickets from past events!");
        if(!attendee.getTickets().contains(ticket))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket not assigned to attendee!");
        attendee.getTickets().remove(ticket);
        attendee.setBalance(attendee.getBalance() + ticket.getPrice());
        if(ticket.getType() == TicketType.FREE)
            entity.setFreeTicketsSelled(entity.getAmountFreeTicketsSelled()-1);
        else
            entity.setFreeTicketsSelled(entity.getAmountFreeTicketsSelled()-1);
        eventRepo.save(entity);
        attendeeRepo.save(attendee);
        ticketRepo.delete(ticket);
    }

    public EventDTO update(Long id, EventUpdateDTO updateDTO) {

        if (updateDTO.getStartDate().compareTo(updateDTO.getEndDate()) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The end date should be bigger than the start date!");
        } 
        else if (updateDTO.getEndDate().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot update past events!");
>>>>>>> AF
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
<<<<<<< HEAD
                entity.setTickets(updateDTO.getTickets());
                entity.setPlaces(updateDTO.getPlaces());

                entity = eventRepo.save(entity);
=======

                if(!isDateTimeValid(entity))
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Confilicting date time!");

                for(Ticket t : entity.getTickets()){
                    if(t.getType() == TicketType.PAYED && t.getAttendee() == null)
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
>>>>>>> AF
                return new EventDTO(entity);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }
        }
    }

<<<<<<< HEAD
    private boolean isDateTimeValid(EventInsertDTO insertDTO) {
        boolean b = true;

        //se o horario inicial for maior que o horario final e estiverem no mesmo dia 
        if(insertDTO.getStartTime().isAfter(insertDTO.getEndTime()) && insertDTO.getStartDate().compareTo(insertDTO.getEndDate()) == 0)
            b = false;
=======
    private boolean isDateTimeValid(Event event) {
        boolean b = true;

        //se o horario inicial for maior que o horario final e estiverem no mesmo dia 
        if(event.getStartTime().isAfter(event.getEndTime()) && event.getStartDate().compareTo(event.getEndDate()) == 0)
        {
            b = false;
            return b;
        }
>>>>>>> AF
        for(Event e: eventRepo.findAll()) {

            LocalTime startT = e.getStartTime();
            LocalTime endT = e.getEndTime();
            LocalDate startD = e.getStartDate();
            LocalDate endD = e.getEndDate();
            for(Place p : e.getPlaces()) {
<<<<<<< HEAD
                if(p.getId() == insertDTO.getPlaceId()) {

                
                
            
=======
                if(event.getPlaces().contains(p) && e.getId() != event.getId()) {

>>>>>>> AF
                    //se a data INICIAL inserida for IGUAL a data INICIAL ja alocada OU se a data FINAL inserida for IGUAL a data FINAL ja alocada OU 
                    //se a data INICIAL inserida for IGUAL a data FINAL ja alocada OU se a data FINAL inserida for IGUAL a data INICIAL ja alocada OU 
                    //se a data INICIAL inserida estiver DEPOIS da data FINAL ja alocada E se a data INICIAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada E se a data FINAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data INICIAL inserida estiver ANTES da data INICIAL ja alocada E se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada
<<<<<<< HEAD
                    if(insertDTO.getStartDate().compareTo(startD) == 0 || insertDTO.getEndDate().compareTo(endD) == 0 ||
                       insertDTO.getStartDate().compareTo(endD) == 0 || insertDTO.getEndDate().compareTo(startD) == 0 ||
                       insertDTO.getStartDate().isAfter(startD) &&  insertDTO.getStartDate().isBefore(endD) || 
                       insertDTO.getEndDate().isAfter(startD) && insertDTO.getEndDate().isBefore(endD) || 
                       insertDTO.getStartDate().isBefore(startD) && insertDTO.getEndDate().isAfter(endD)) {

                        //se o horario INICIAL inserido for IGUAL o horario INICIAL ja alocado OU se o horario FINAL inserido for IGUAL o horario FINAL ja alocado OU 
                        //se o horario INICIAL inserido for IGUAL o horario FINAL ja alocado OU se o horario FINAL inserido for IGUAL o horario INICIAL ja alocado OU 
                        if(insertDTO.getStartTime().compareTo(startT) == 0 || insertDTO.getEndTime().compareTo(endT) == 0  || 
                            insertDTO.getStartTime().compareTo(endT) == 0 || insertDTO.getEndTime ().compareTo(startT) == 0) {
=======
                    if(event.getStartDate().compareTo(startD) == 0 || event.getEndDate().compareTo(endD) == 0 ||
                    event.getStartDate().compareTo(endD) == 0 || event.getEndDate().compareTo(startD) == 0 ||
                    event.getStartDate().isAfter(startD) &&  event.getStartDate().isBefore(endD) || 
                    event.getEndDate().isAfter(startD) && event.getEndDate().isBefore(endD) || 
                    event.getStartDate().isBefore(startD) && event.getEndDate().isAfter(endD)) {

                        //se o horario INICIAL inserido for IGUAL o horario INICIAL ja alocado OU se o horario FINAL inserido for IGUAL o horario FINAL ja alocado OU 
                        //se o horario INICIAL inserido for IGUAL o horario FINAL ja alocado OU se o horario FINAL inserido for IGUAL o horario INICIAL ja alocado OU 
                        if(event.getStartTime().compareTo(startT) == 0 || event.getEndTime().compareTo(endT) == 0  || 
                            event.getStartTime().compareTo(endT) == 0 || event.getEndTime ().compareTo(startT) == 0) {
>>>>>>> AF
                            b = false;
                            break;
                        }

                        //se o horario INICIAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario INICIAL inserido estiver ANTES do horario FINAL ja alocado OU
                        //se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario FINAL inserido estiver ANTES do horario FINAL ja alocado OU
<<<<<<< HEAD
                        else if(insertDTO.getStartTime().isAfter(startT) &&  insertDTO.getStartTime().isBefore(endT) || 
                                insertDTO.getEndTime().isAfter(startT) && insertDTO.getEndTime().isBefore(endT)){
=======
                        else if(event.getStartTime().isAfter(startT) &&  event.getStartTime().isBefore(endT) || 
                                event.getEndTime().isAfter(startT) && event.getEndTime().isBefore(endT)){
>>>>>>> AF
                                b = false;
                                break;
                            }   

                        //se o horario INICIAL inserido estiver ANTES do horario INICIAL ja alocado E se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado
<<<<<<< HEAD
                        else if(insertDTO.getStartTime().isBefore(startT) && insertDTO.getEndTime().isAfter(endT)){
=======
                        else if(event.getStartTime().isBefore(startT) && event.getEndTime().isAfter(endT)){
>>>>>>> AF
                            b = false;
                            break;
                        }
                    }
                }
            }
<<<<<<< HEAD
    }
        return b;
    }

    private boolean isDateTimeValid(EventUpdateDTO updateDTO) {
        boolean b = true;
        //se o horario inicial for maior que o horario final e estiverem no mesmo dia 
        if(updateDTO.getStartTime().isAfter(updateDTO.getEndTime()) && updateDTO.getStartDate().compareTo(updateDTO.getEndDate()) == 0)
            b = false;
        for(Event e: eventRepo.findAll()) {

            LocalTime startT = e.getStartTime();
            LocalTime endT = e.getEndTime();
            LocalDate startD = e.getStartDate();
            LocalDate endD = e.getEndDate();

            for(Place p: placeRepo.findAll())
            {
                if (p.getId() != updateDTO.getPlaceId()) {

                    //se a data INICIAL inserida for IGUAL a data INICIAL ja alocada OU se a data FINAL inserida for IGUAL a data FINAL ja alocada OU 
                    //se a data INICIAL inserida for IGUAL a data FINAL ja alocada OU se a data FINAL inserida for IGUAL a data INICIAL ja alocada OU 
                    //se a data INICIAL inserida estiver DEPOIS da data FINAL ja alocada E se a data INICIAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada E se a data FINAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data INICIAL inserida estiver ANTES da data INICIAL ja alocada E se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada
                    if(updateDTO.getStartDate().compareTo(startD) == 0 || updateDTO.getEndDate().compareTo(endD) == 0 ||
                        updateDTO.getStartDate().compareTo(endD) == 0 || updateDTO.getEndDate().compareTo(startD) == 0 ||
                        updateDTO.getStartDate().isAfter(startD) &&  updateDTO.getStartDate().isBefore(endD) || 
                        updateDTO.getEndDate().isAfter(startD) && updateDTO.getEndDate().isBefore(endD) || 
                        updateDTO.getStartDate().isBefore(startD) && updateDTO.getEndDate().isAfter(endD)) {

                        //se o horario INICIAL inserido for IGUAL o horario INICIAL ja alocado OU se o horario FINAL inserido for IGUAL o horario FINAL ja alocado OU 
                        //se o horario INICIAL inserido for IGUAL o horario FINAL ja alocado OU se o horario FINAL inserido for IGUAL o horario INICIAL ja alocado OU 
                        if(updateDTO.getStartTime().compareTo(startT) == 0 || updateDTO.getEndTime().compareTo(endT) == 0  || 
                            updateDTO.getStartTime().compareTo(endT) == 0 || updateDTO.getEndTime ().compareTo(startT) == 0)
                            b = false;

                        //se o horario INICIAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario INICIAL inserido estiver ANTES do horario FINAL ja alocado OU
                        //se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario FINAL inserido estiver ANTES do horario FINAL ja alocado OU
                        else if(updateDTO.getStartTime().isAfter(startT) &&  updateDTO.getStartTime().isBefore(endT) || 
                                updateDTO.getEndTime().isAfter(startT) && updateDTO.getEndTime().isBefore(endT))
                                b = false;   

                        //se o horario INICIAL inserido estiver ANTES do horario INICIAL ja alocado E se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado
                        else if(updateDTO.getStartTime().isBefore(startT) && updateDTO.getEndTime().isAfter(endT))
                            b = false;
                    }
                }
            }
=======
>>>>>>> AF
        }
        return b;
    }

<<<<<<< HEAD

=======
>>>>>>> AF
}
