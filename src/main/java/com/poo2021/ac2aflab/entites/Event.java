package com.poo2021.ac2aflab.entites;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poo2021.ac2aflab.dto.Event.EventInsertDTO;

@Entity
@Table(name="TB_EVENT")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String emailContact;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTicket;

    private Long FreeTicketsSelled;
    private Long PayedTicketsSelled;

    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="ADMIN_BASEUSER_ID")
    private Admin admin;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="TICKET_ID")
    private List<Ticket> tickets = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name="TB_EVENT_PLACE",
            joinColumns =  @JoinColumn(name="EVENT_ID"),
            inverseJoinColumns = @JoinColumn(name="PLACE_ID")
    )
    private List<Place> places = new ArrayList<>();

    public Event() {

    }

    public Event(Long id, String name, String description, LocalDate startDate, LocalDate endDate, LocalTime startTime,
            LocalTime endTime, String emailContact, Long amountFreeTickets, Long amountPayedTickets, Double priceTicket,
            Long freeTicketsSelled, Long payedTicketsSelled, Admin admin, List<Ticket> tickets, List<Place> places) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.emailContact = emailContact;
        this.amountFreeTickets = amountFreeTickets;
        this.amountPayedTickets = amountPayedTickets;
        this.priceTicket = priceTicket;
    }

    public Event(EventInsertDTO insertDTO) {
    this.name = insertDTO.getName();
    this.description = insertDTO.getDescription();
    this.startDate = insertDTO.getStartDate();
    this.endDate = insertDTO.getEndDate();
    this.startTime = insertDTO.getStartTime();
    this.endTime = insertDTO.getEndTime();
    this.emailContact = insertDTO.getEmailContact();
    this.amountFreeTickets = insertDTO.getAmountFreeTickets();
    this.amountPayedTickets = insertDTO.getAmountPayedTickets();
    this.priceTicket = insertDTO.getPriceTicket();
    this.admin = insertDTO.getAdmin();
    this.tickets = insertDTO.getTickets();
    this.places = insertDTO.getPlaces();
    }

    public Long getAmountFreeTicketsSelled(Event event, Long amoutFreeTickets) {
        event.FreeTicketsSelled ++;
        return event.FreeTicketsSelled;

    }

    public Long getAmountPayedTicketsSelled(Event event, Long amoutPayedTickets) {
        event.PayedTicketsSelled ++;
        return event.PayedTicketsSelled;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public Long getAmountFreeTickets() {
        return amountFreeTickets;
    }

    public void setAmountFreeTickets(Long amountFreeTickets) {
        this.amountFreeTickets = amountFreeTickets;
    }

    public Long getAmountPayedTickets() {
        return amountPayedTickets;
    }

    public void setAmountPayedTickets(Long amountPayedTickets) {
        this.amountPayedTickets = amountPayedTickets;
    }

    public Double getPriceTicket() {
        return priceTicket;
    }

    public void setPriceTicket(Double priceTicket) {
        this.priceTicket = priceTicket;
    }

    public Long getFreeTicketsSelled() {
        return FreeTicketsSelled;
    }

    public void setFreeTicketsSelled(Long freeTicketsSelled) {
        FreeTicketsSelled = freeTicketsSelled;
    }

    public Long getPayedTicketsSelled() {
        return PayedTicketsSelled;
    }

    public void setPayedTicketsSelled(Long payedTicketsSelled) {
        PayedTicketsSelled = payedTicketsSelled;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
