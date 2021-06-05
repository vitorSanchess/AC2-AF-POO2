package com.poo2021.ac2aflab.dto.Ticket;

import java.time.Instant;

import com.poo2021.ac2aflab.entites.Attendee;
import com.poo2021.ac2aflab.entites.Event;

public class TicketDTO {


    private Long id;

    private enum TicketType{
        FREE, PAYED
    }
    private TicketType type;
    private Instant date;
    private Double price;

    private Event event;

    private Attendee attendee;

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
