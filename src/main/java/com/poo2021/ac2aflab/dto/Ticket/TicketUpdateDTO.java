package com.poo2021.ac2aflab.dto.Ticket;

import java.time.Instant;

import com.poo2021.ac2aflab.entites.Attendee;
import com.poo2021.ac2aflab.entites.Event;

public class TicketUpdateDTO {

    private Double price;

    private Attendee attendee;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public void setAttendee(Attendee attendee) {
        this.attendee = attendee;
    }

    
}
