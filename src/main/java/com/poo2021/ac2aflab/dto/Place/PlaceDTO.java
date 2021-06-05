package com.poo2021.ac2aflab.dto.Place;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.entites.Event;
import com.poo2021.ac2aflab.entites.Place;

public class PlaceDTO {
    
    private Long id;
    private String name;
    private String address;

    private List<Event> events = new ArrayList<>();

    public PlaceDTO() {

    }

    public PlaceDTO(Long id, String name) {
        setId(id);
        setName(name);
    }

    public PlaceDTO(Place place) {
        setId(place.getId());
        setName(place.getName());
        setAddress(place.getAddress());
        setEvents(place.getEvents());
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    
}
