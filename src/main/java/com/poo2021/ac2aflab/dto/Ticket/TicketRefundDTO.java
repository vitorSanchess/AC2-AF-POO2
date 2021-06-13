package com.poo2021.ac2aflab.dto.Ticket;

import com.poo2021.ac2aflab.entites.Ticket.TicketType;

public class TicketRefundDTO {

    private Long attendeeId;

    private Long TicketId;

    public Long getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public Long getTicketId() {
        return TicketId;
    }

    public void setTicketId(Long ticketId) {
        TicketId = ticketId;
    }
    
}
