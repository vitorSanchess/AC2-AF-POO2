package com.poo2021.ac2aflab.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.poo2021.ac2aflab.dto.EventDTO;
import com.poo2021.ac2aflab.dto.EventInsertDTO;
import com.poo2021.ac2aflab.dto.EventUpdateDTO;
import com.poo2021.ac2aflab.entites.Event;
import com.poo2021.ac2aflab.entites.Place;
import com.poo2021.ac2aflab.entites.Admin;
import com.poo2021.ac2aflab.repositories.AdminRepository;
import com.poo2021.ac2aflab.repositories.EventRepository;
import com.poo2021.ac2aflab.repositories.PlaceRepository;


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
    private PlaceRepository placeRepo;

    public Page<EventDTO> getEvents(PageRequest pageRequest, String name, String description) {
        Page<Event> list = eventRepo.find(pageRequest, name, description);
        return list.map( a -> new EventDTO(a));
    }

    public EventDTO getEventById(Long id) {
        Optional<Event> op = eventRepo.findById(id);
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
        try {
            eventRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

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
                entity.setTickets(updateDTO.getTickets());
                entity.setPlaces(updateDTO.getPlaces());

                entity = eventRepo.save(entity);
                return new EventDTO(entity);
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }
        }
    }

    private boolean isDateTimeValid(EventInsertDTO insertDTO) {
        boolean b = true;

        //se o horario inicial for maior que o horario final e estiverem no mesmo dia 
        if(insertDTO.getStartTime().isAfter(insertDTO.getEndTime()) && insertDTO.getStartDate().compareTo(insertDTO.getEndDate()) == 0)
            b = false;
        for(Event e: eventRepo.findAll()) {

            LocalTime startT = e.getStartTime();
            LocalTime endT = e.getEndTime();
            LocalDate startD = e.getStartDate();
            LocalDate endD = e.getEndDate();
            for(Place p : e.getPlaces()) {
                if(p.getId() == insertDTO.getPlaceId()) {

                
                
            
                    //se a data INICIAL inserida for IGUAL a data INICIAL ja alocada OU se a data FINAL inserida for IGUAL a data FINAL ja alocada OU 
                    //se a data INICIAL inserida for IGUAL a data FINAL ja alocada OU se a data FINAL inserida for IGUAL a data INICIAL ja alocada OU 
                    //se a data INICIAL inserida estiver DEPOIS da data FINAL ja alocada E se a data INICIAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada E se a data FINAL inserida estiver ANTES da data FINAL ja alocada OU
                    //se a data INICIAL inserida estiver ANTES da data INICIAL ja alocada E se a data FINAL inserida estiver DEPOIS da data FINAL ja alocada
                    if(insertDTO.getStartDate().compareTo(startD) == 0 || insertDTO.getEndDate().compareTo(endD) == 0 ||
                       insertDTO.getStartDate().compareTo(endD) == 0 || insertDTO.getEndDate().compareTo(startD) == 0 ||
                       insertDTO.getStartDate().isAfter(startD) &&  insertDTO.getStartDate().isBefore(endD) || 
                       insertDTO.getEndDate().isAfter(startD) && insertDTO.getEndDate().isBefore(endD) || 
                       insertDTO.getStartDate().isBefore(startD) && insertDTO.getEndDate().isAfter(endD)) {

                        //se o horario INICIAL inserido for IGUAL o horario INICIAL ja alocado OU se o horario FINAL inserido for IGUAL o horario FINAL ja alocado OU 
                        //se o horario INICIAL inserido for IGUAL o horario FINAL ja alocado OU se o horario FINAL inserido for IGUAL o horario INICIAL ja alocado OU 
                        if(insertDTO.getStartTime().compareTo(startT) == 0 || insertDTO.getEndTime().compareTo(endT) == 0  || 
                            insertDTO.getStartTime().compareTo(endT) == 0 || insertDTO.getEndTime ().compareTo(startT) == 0) {
                            b = false;
                            break;
                        }

                        //se o horario INICIAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario INICIAL inserido estiver ANTES do horario FINAL ja alocado OU
                        //se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado E se o horario FINAL inserido estiver ANTES do horario FINAL ja alocado OU
                        else if(insertDTO.getStartTime().isAfter(startT) &&  insertDTO.getStartTime().isBefore(endT) || 
                                insertDTO.getEndTime().isAfter(startT) && insertDTO.getEndTime().isBefore(endT)){
                                b = false;
                                break;
                            }   

                        //se o horario INICIAL inserido estiver ANTES do horario INICIAL ja alocado E se o horario FINAL inserido estiver DEPOIS do horario FINAL ja alocado
                        else if(insertDTO.getStartTime().isBefore(startT) && insertDTO.getEndTime().isAfter(endT)){
                            b = false;
                            break;
                        }
                    }
                }
            }
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
        }
        return b;
    }


}