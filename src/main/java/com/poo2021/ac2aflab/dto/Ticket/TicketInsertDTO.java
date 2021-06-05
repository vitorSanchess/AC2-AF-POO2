package com.poo2021.ac2aflab.dto.Ticket;

import java.time.Instant;

public class TicketInsertDTO {

    private enum TicketType{
        FREE, PAYED
    }
    private TicketType type;
    private Instant date;
    private Double price;

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
}
