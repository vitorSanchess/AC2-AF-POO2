package com.poo2021.ac2aflab.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.poo2021.ac2aflab.dto.EventDTO;
import com.poo2021.ac2aflab.dto.EventInsertDTO;
import com.poo2021.ac2aflab.dto.EventUpdateDTO;
import com.poo2021.ac2aflab.entites.Event;
import com.poo2021.ac2aflab.entites.Admin;
import com.poo2021.ac2aflab.repositories.AdminRepository;
import com.poo2021.ac2aflab.repositories.EventRepository;

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
        Event entity = new Event(insertDTO);
        for(Admin admin : adminRepo.findAll())
        {
            if(admin.getId() == insertDTO.getAdminId())
                entity.setAdmin(admin);
                
        }
        entity = eventRepo.save(entity);
        return new EventDTO(entity);
    }

    public void delete(Long id) {
        try {
            eventRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
    }

    public EventDTO update(Long id, EventUpdateDTO updateDTO) {
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
