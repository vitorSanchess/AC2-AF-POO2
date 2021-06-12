package com.poo2021.ac2aflab.dto.Event;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventUpdateDTO {
    
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long amountFreeTickets;
    private Long amountPayedTickets;
    private Double priceTicket;

    private Long oldPlaceId;
    private Long newPlaceId;

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

    public Long getOldPlaceId() {
        return oldPlaceId;
    }

    public void setOldPlaceId(Long oldPlaceId) {
        this.oldPlaceId = oldPlaceId;
    }

    public Long getNewPlaceId() {
        return newPlaceId;
    }

    public void setNewPlaceId(Long newPlaceId) {
        this.newPlaceId = newPlaceId;
    }

}
