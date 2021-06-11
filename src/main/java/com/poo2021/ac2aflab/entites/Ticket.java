package com.poo2021.ac2aflab.entites;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private enum TicketType{
        FREE, PAYED
    }
    private TicketType type;
    private Instant date;
    private Double price;

    @ManyToOne
    @JoinColumn(name="EVENT_ID")
    private Event event;

    @ManyToOne
    @JoinColumn(name="ATTEND_BASEUSER_ID")
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

}
