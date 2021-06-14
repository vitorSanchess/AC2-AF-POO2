package com.poo2021.ac2aflab.services;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.dto.Ticket.TicketDTO;
import com.poo2021.ac2aflab.entites.Ticket;
import com.poo2021.ac2aflab.repositories.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    
    @Autowired
    private TicketRepository repo;

    public Page<TicketDTO> getTickets(PageRequest pageRequest, Double price) {
        Page<Ticket> list = repo.find(pageRequest, price);
        return list.map( a -> new TicketDTO(a));
    }

    public TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setType(ticket.getType());
        dto.setDate(ticket.getDate());
        dto.setPrice(ticket.getPrice());
        dto.setEvent(ticket.getEvent());
        dto.setAttendee(ticket.getAttendee());
        return dto;
    }

    public List<TicketDTO> toDTOList(List<Ticket> list) {
        List<TicketDTO> listDTO = new ArrayList<>();

        for (Ticket a : list) {
            listDTO.add(toDTO(a)); 
        }
        return listDTO;
    }

}
