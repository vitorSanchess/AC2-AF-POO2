package com.poo2021.ac2aflab.repositories;


import com.poo2021.ac2aflab.entites.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    @Query("SELECT a FROM Admin a " + 
    "WHERE " +
    " LOWER(a.name)               LIKE   LOWER(CONCAT('%', :name, '%')) AND " +
    " LOWER(a.email)        LIKE   LOWER(CONCAT('%', :email, '%')) AND " +
    " LOWER(a.phoneNumber)              LIKE   LOWER(CONCAT('%', :phoneNumber, '%'))")

    public Page<Admin> find(Pageable pageRequest, String name, String email, String phoneNumber);
    
}
