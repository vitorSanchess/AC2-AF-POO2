package com.poo2021.ac2aflab.dto;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.entites.Event;

public class AdminUpdateDTO {
    
    private String name;
    private String phoneNumber;

    private List<Event> events = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

   

    
}
