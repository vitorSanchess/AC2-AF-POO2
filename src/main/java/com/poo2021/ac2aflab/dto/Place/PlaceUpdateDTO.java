package com.poo2021.ac2aflab.dto.Place;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.entites.Event;

public class PlaceUpdateDTO {
    
    private String name;

    private List<Event> events = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    
}
