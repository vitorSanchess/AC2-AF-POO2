package com.poo2021.ac2aflab.dto;

import java.util.ArrayList;
import java.util.List;

import com.poo2021.ac2aflab.entites.Admin;
import com.poo2021.ac2aflab.entites.Event;

public class AdminDTO {
    
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    private List<Event> events = new ArrayList<>();

    public AdminDTO() {

    }

    public AdminDTO(Long id, String name) {
        setId(id);
        setName(name);
    }

    public AdminDTO(Admin admin) {
        setId(admin.getId());
        setName(admin.getName());
        setEmail(admin.getEmail());
        setPhoneNumber(admin.getPhoneNumber());
        setEvents(admin.getEvents());
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
