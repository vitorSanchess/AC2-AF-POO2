package com.poo2021.ac2aflab.repositories;


import java.time.Instant;

import com.poo2021.ac2aflab.entites.Ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    public Page<Ticket> find(Pageable pageRequest, Instant date, Double price);
    
}
