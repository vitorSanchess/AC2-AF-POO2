package com.poo2021.ac2aflab.repositories;


import java.time.Instant;

import com.poo2021.ac2aflab.entites.Ticket;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query("SELECT i FROM Ticket i " + 
    "WHERE " +
    "  i.date >= :date AND " +
    "  i.price >= :price")

    public Page<Ticket> find(Pageable pageRequest, Instant date, Double price);
    
}
