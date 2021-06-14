package com.poo2021.ac2aflab.controller;


import com.poo2021.ac2aflab.dto.Ticket.TicketDTO;
import com.poo2021.ac2aflab.services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/tickets")
public class TicketController {
    
    @Autowired
    private TicketService service;

    @GetMapping
    public ResponseEntity<Page<TicketDTO>> getTickets(
        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "price",      defaultValue = "0.0") Double price
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        
        Page<TicketDTO> list = service.getTickets(pageRequest, price);

        return ResponseEntity.ok().body(list);
    }

}
