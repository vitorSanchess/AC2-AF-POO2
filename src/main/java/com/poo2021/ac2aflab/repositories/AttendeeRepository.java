package com.poo2021.ac2aflab.repositories;


import com.poo2021.ac2aflab.entites.Attendee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee,Long> {

    @Query("SELECT t FROM Attendee t " + 
    "WHERE " +
    " LOWER(t.name)         LIKE   LOWER(CONCAT('%', :name, '%')) AND " +
    " LOWER(t.email)        LIKE   LOWER(CONCAT('%', :email, '%')) AND " +
    " t.balance >= :balance")

    public Page<Attendee> find(Pageable pageRequest, String name, String email, Double balance);
    
}
