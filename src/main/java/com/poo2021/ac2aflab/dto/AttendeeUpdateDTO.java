package com.poo2021.ac2aflab.dto;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.entites.Event;

public class AttendeeUpdateDTO {
    
    private String name;
    private Double balance;

    private List<Event> events = new ArrayList<>();

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    
}
