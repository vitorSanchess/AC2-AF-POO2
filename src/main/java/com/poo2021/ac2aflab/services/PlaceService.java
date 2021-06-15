package com.poo2021.ac2aflab.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

<<<<<<< HEAD
import com.poo2021.ac2aflab.dto.PlaceDTO;
import com.poo2021.ac2aflab.dto.PlaceInsertDTO;
import com.poo2021.ac2aflab.dto.PlaceUpdateDTO;
=======
import com.poo2021.ac2aflab.dto.Place.PlaceDTO;
import com.poo2021.ac2aflab.dto.Place.PlaceInsertDTO;
import com.poo2021.ac2aflab.dto.Place.PlaceUpdateDTO;
>>>>>>> AF
import com.poo2021.ac2aflab.entites.Place;
import com.poo2021.ac2aflab.repositories.PlaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlaceService {
    
    @Autowired
<<<<<<< HEAD
    private PlaceRepository repo;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name, String address) {
        Page<Place> list = repo.find(pageRequest, name, address);
=======
    private PlaceRepository placeRepo;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name, String address) {
        Page<Place> list = placeRepo.find(pageRequest, name, address);
>>>>>>> AF
        return list.map( a -> new PlaceDTO(a));
    }

    public PlaceDTO getPlaceById(Long id) {
<<<<<<< HEAD
        Optional<Place> op = repo.findById(id);
=======
        Optional<Place> op = placeRepo.findById(id);
>>>>>>> AF
        Place Place = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return new PlaceDTO(Place);
    }

    public PlaceDTO insert(PlaceInsertDTO insertDTO) {
        Place entity = new Place(insertDTO);
<<<<<<< HEAD
        entity = repo.save(entity);
=======
        entity = placeRepo.save(entity);
>>>>>>> AF
        return new PlaceDTO(entity);
    }

    public void delete(Long id) {
<<<<<<< HEAD
        try {
            repo.deleteById(id);
=======
        if(!placeRepo.findById(id).get().getEvents().isEmpty())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't delete a place that has been used");
        try {
            placeRepo.deleteById(id);
>>>>>>> AF
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public PlaceDTO update(Long id, PlaceUpdateDTO updateDTO) {
        try {
<<<<<<< HEAD
            Place entity = repo.getOne(id);
=======
            Place entity = placeRepo.getOne(id);
>>>>>>> AF

            entity.setName(updateDTO.getName());
            entity.setEvents(updateDTO.getEvents());

<<<<<<< HEAD
            entity = repo.save(entity);
=======
            entity = placeRepo.save(entity);
>>>>>>> AF
            return new PlaceDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }
    

}
