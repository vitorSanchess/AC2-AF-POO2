package com.poo2021.ac2aflab.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.poo2021.ac2aflab.dto.AttendeeInsertDTO;

@Entity
@Table(name="TB_ATTEND")
@PrimaryKeyJoinColumn(name="BASEUSER_ID")
public class Attendee extends BaseUser {
    
    private Double balance;

    @OneToMany
    @JoinColumn(name="TICKET_ID")
    private List<Ticket> tickets = new ArrayList<>();

    public Attendee() {

    }

    public Attendee(Double balance) {
        this.balance = balance;
    }

    public Attendee(Long id, String name, String email, Double balance) {
        super(id,name,email);
        this.balance = balance;
    }

    public Attendee(AttendeeInsertDTO insertDTO) {
        super(insertDTO.getName(), insertDTO.getEmail());
        this.balance = 0.0;
	}

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    
}
