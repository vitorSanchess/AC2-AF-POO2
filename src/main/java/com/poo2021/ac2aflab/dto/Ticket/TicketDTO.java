package com.poo2021.ac2aflab.dto.Ticket;

import java.time.Instant;

import com.poo2021.ac2aflab.entites.Attendee;
import com.poo2021.ac2aflab.entites.Event;
import com.poo2021.ac2aflab.entites.Ticket;
import com.poo2021.ac2aflab.entites.Ticket.TicketType;

public class TicketDTO {


    private Long id;

    private TicketType type;
    private Instant date;
    private Double price;

    private Event event;

    private Attendee attendee;

    public TicketDTO() {

    }

    public TicketDTO(Long id, TicketType type) {
        setId(id);
        setType(type);
    }

    public TicketDTO(Ticket ticket) {
        setId(ticket.getId());
        setDate(ticket.getDate());
        setPrice(ticket.getPrice());
        setEvent(ticket.getEvent());
        setAttendee(ticket.getAttendee());
    }

    public TicketDTO(String name) { //adicionar tipo
        //setType(type);
        attendee.setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }

    
}
