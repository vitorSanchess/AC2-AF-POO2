package com.poo2021.ac2aflab.dto.Attendee;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.entites.Ticket;

public class AttendeeUpdateDTO {
    
    private String name;
    private Double balance;

    private List<Ticket> tickets = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
