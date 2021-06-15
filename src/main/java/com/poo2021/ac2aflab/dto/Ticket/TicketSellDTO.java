package com.poo2021.ac2aflab.dto.Ticket;

import com.poo2021.ac2aflab.entites.Ticket.TicketType;

public class TicketSellDTO {

    private Long attendeeId;

    private TicketType type;

    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    
    
}
