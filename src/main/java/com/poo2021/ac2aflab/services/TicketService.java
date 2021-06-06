package com.poo2021.ac2aflab.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.poo2021.ac2aflab.dto.Ticket.TicketDTO;
import com.poo2021.ac2aflab.dto.Ticket.TicketInsertDTO;
import com.poo2021.ac2aflab.dto.Ticket.TicketUpdateDTO;
import com.poo2021.ac2aflab.entites.Ticket;
import com.poo2021.ac2aflab.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository repo;

    public Page<TicketDTO> getTickets(PageRequest pageRequest, Double price) {
        Page<Ticket> list = repo.find(pageRequest, price);
        return list.map( a -> new TicketDTO(a));
    }

    public TicketDTO getTicketById(Long id) {
        Optional<Ticket> op = repo.findById(id);
        Ticket Ticket = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found"));
        return new TicketDTO(Ticket);
    }

    public List<Ticket> getAllTickets() {
        return repo.findAll();
    }

    public TicketDTO insert(TicketInsertDTO insertDTO) {
        Ticket entity = new Ticket(insertDTO);
        entity = repo.save(entity);
        return new TicketDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }
    }

    public TicketDTO update(Long id, TicketUpdateDTO updateDTO) {
        try {
            Ticket entity = repo.getOne(id);

            entity.setAttendee(updateDTO.getAttendee());
            entity.setPrice(updateDTO.getPrice());

            entity = repo.save(entity);
            return new TicketDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found");
        }
    }

    public TicketDTO toDTO(Ticket ticket) { //adicionar tipo de ingresso
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setEvent(ticket.getEvent());
        dto.setAttendee(ticket.getAttendee());
        return dto;
    }

    public List<TicketDTO> toDTOList(List<Ticket> list) {
        List<TicketDTO> listDTO = new ArrayList<>();

        for (Ticket a : list) {
            listDTO.add(toDTO(a)); //adicionar tipo
        }
        return listDTO;
    }

}
