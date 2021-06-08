package com.poo2021.ac2aflab.entites;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poo2021.ac2aflab.dto.Ticket.TicketInsertDTO;

@Entity
@Table(name="TB_TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum TicketType{
        FREE, PAYED
    }
    private TicketType type;
    private Instant date;
    private Double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="EVENT_ID")
    private Event event;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="ATTEND_BASEUSER_ID")
    private Attendee attendee;

    public Ticket() {

    }

    

    public Ticket(Long id, TicketType type, Instant date, Double price, Event event, Attendee attendee) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.price = price;
        this.event = event;
        this.attendee = attendee;
    }

    public Ticket(TicketInsertDTO insertDTO) {
        //this.type = insertDTO.getType();
        this.date = insertDTO.getDate();
        this.price = insertDTO.getPrice();
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
