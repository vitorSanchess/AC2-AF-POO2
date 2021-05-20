package com.poo2021.ac2aflab.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.poo2021.ac2aflab.dto.AdminInsertDTO;

@Entity
@Table(name="TB_ADMIN")
@PrimaryKeyJoinColumn(name="BASEUSER_ID")
public class Admin extends BaseUser {
    
    public String phoneNumber;

    @OneToMany(mappedBy = "admin")
    private List<Event> events = new ArrayList<>();

    public Admin() {

    }

    public Admin(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Admin(Long id, String name, String email, String phoneNumber) {
        super(id,name,email);
        this.phoneNumber = phoneNumber;
    }

    public Admin(AdminInsertDTO insertDTO) {
        super(insertDTO.getName(), insertDTO.getEmail());
        this.phoneNumber = insertDTO.getPhoneNumber();
	}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    
}
