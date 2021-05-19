package com.poo2021.ac2aflab.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TB_ATTEND")
@PrimaryKeyJoinColumn(name="BASEUSER_ID")
public class Attend extends BaseUser {
    
    private Double balance;

    @OneToMany
    @JoinColumn(name="TICKET_ID")
    private List<Ticket> tickets = new ArrayList<>();

    public Attend() {

    }

    public Attend(Double balance) {
        this.balance = balance;
    }

    public Attend(Long id, String name, String email, Double balance) {
        super(id,name,email);
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


}
