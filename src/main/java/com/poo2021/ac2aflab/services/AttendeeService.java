package com.poo2021.ac2aflab.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

<<<<<<< HEAD
import com.poo2021.ac2aflab.dto.AttendeeDTO;
import com.poo2021.ac2aflab.dto.AttendeeInsertDTO;
import com.poo2021.ac2aflab.dto.AttendeeUpdateDTO;
=======
import com.poo2021.ac2aflab.dto.Attendee.AttendeeDTO;
import com.poo2021.ac2aflab.dto.Attendee.AttendeeInsertDTO;
import com.poo2021.ac2aflab.dto.Attendee.AttendeeUpdateDTO;
>>>>>>> AF
import com.poo2021.ac2aflab.entites.Attendee;
import com.poo2021.ac2aflab.repositories.AttendeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendeeService {
    
    @Autowired
    private AttendeeRepository repo;

    public Page<AttendeeDTO> getAttendees(PageRequest pageRequest, String name, String email, Double balance) {
        Page<Attendee> list = repo.find(pageRequest, name, email, balance);
        return list.map( a -> new AttendeeDTO(a));
    }

    public AttendeeDTO getAttendeeById(Long id) {
        Optional<Attendee> op = repo.findById(id);
        Attendee attendee = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found"));
        return new AttendeeDTO(attendee);
    }

    public AttendeeDTO insert(AttendeeInsertDTO insertDTO) {
        Attendee entity = new Attendee(insertDTO);
        entity = repo.save(entity);
        return new AttendeeDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }

    public AttendeeDTO update(Long id, AttendeeUpdateDTO updateDTO) {
        try {
            Attendee entity = repo.getOne(id);

            entity.setName(updateDTO.getName());
            entity.setBalance(updateDTO.getBalance());
            entity.setTickets(updateDTO.getTickets());
<<<<<<< HEAD

=======
        
>>>>>>> AF
            entity = repo.save(entity);
            return new AttendeeDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendee not found");
        }
    }
    

}
