package com.poo2021.ac2aflab.services;

// import java.util.ArrayList;
// import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.poo2021.ac2aflab.dto.Admin.AdminDTO;
import com.poo2021.ac2aflab.dto.Admin.AdminInsertDTO;
import com.poo2021.ac2aflab.dto.Admin.AdminUpdateDTO;
import com.poo2021.ac2aflab.entites.Admin;
import com.poo2021.ac2aflab.repositories.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository repo;

    public Page<AdminDTO> getAdmins(PageRequest pageRequest, String name, String email, String phoneNumber) {
        Page<Admin> list = repo.find(pageRequest, name, email, phoneNumber);
        return list.map( a -> new AdminDTO(a));
    }

    public AdminDTO getAdminById(Long id) {
        Optional<Admin> op = repo.findById(id);
        Admin admin = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
        return new AdminDTO(admin);
    }

    public AdminDTO insert(AdminInsertDTO insertDTO) {
        Admin entity = new Admin(insertDTO);
        entity = repo.save(entity);
        return new AdminDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    public AdminDTO update(Long id, AdminUpdateDTO updateDTO) {
        try {
            Admin entity = repo.getOne(id);

            entity.setName(updateDTO.getName());
            entity.setPhoneNumber(updateDTO.getPhoneNumber());
            entity.setEvents(updateDTO.getEvents());

            entity = repo.save(entity);
            return new AdminDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }

    // private List<AdminDTO> toDTOList(List<Admin> list) {
    //     List<AdminDTO> listDTO = new ArrayList<>();

    //     for (Admin a : list) {
    //         listDTO.add(new AdminDTO(a.getId(), a.getName()));
    //     }
    //     return listDTO;
    // }

}
