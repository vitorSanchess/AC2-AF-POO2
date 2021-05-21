package com.poo2021.ac2aflab.dto;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.entites.Attendee;
import com.poo2021.ac2aflab.entites.Ticket;

public class AttendeeDTO {
    
    private Long id;
    private String name;
    private String email;
    private Double balance;

    private List<Ticket> tickets = new ArrayList<>();

    public AttendeeDTO() {

    }

    public AttendeeDTO(Long id, String name) {
        setId(id);
        setName(name);
    }

    public AttendeeDTO(Attendee attendee) {
        setId(attendee.getId());
        setName(attendee.getName());
        setEmail(attendee.getEmail());
        setBalance(attendee.getBalance());
        setTickets(attendee.getTickets());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

        
}
