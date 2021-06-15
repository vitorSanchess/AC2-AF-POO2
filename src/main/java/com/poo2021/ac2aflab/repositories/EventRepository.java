package com.poo2021.ac2aflab.repositories;


<<<<<<< HEAD
=======
import java.time.LocalDate;

>>>>>>> AF
import com.poo2021.ac2aflab.entites.Event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("SELECT e FROM Event e " + 
    "WHERE " +
    " LOWER(e.name)               LIKE   LOWER(CONCAT('%', :name, '%')) AND " +
<<<<<<< HEAD
    " LOWER(e.description)        LIKE   LOWER(CONCAT('%', :description, '%'))")

    public Page<Event> find(Pageable pageRequest, String name, String description);
=======
    " LOWER(e.description)        LIKE   LOWER(CONCAT('%', :description, '%')) AND " +
    " LOWER(e.emailContact)      LIKE   LOWER(CONCAT('%', :emailContact, '%')) AND " +
    " e.startDate > :startDate")

    public Page<Event> find(Pageable pageRequest, String name, String description, String emailContact, LocalDate startDate);
>>>>>>> AF
    
}
