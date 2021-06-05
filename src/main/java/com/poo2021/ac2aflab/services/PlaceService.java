package com.poo2021.ac2aflab.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.poo2021.ac2aflab.dto.Place.PlaceDTO;
import com.poo2021.ac2aflab.dto.Place.PlaceInsertDTO;
import com.poo2021.ac2aflab.dto.Place.PlaceUpdateDTO;
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
    private PlaceRepository repo;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name, String address) {
        Page<Place> list = repo.find(pageRequest, name, address);
        return list.map( a -> new PlaceDTO(a));
    }

    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> op = repo.findById(id);
        Place Place = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return new PlaceDTO(Place);
    }

    public PlaceDTO insert(PlaceInsertDTO insertDTO) {
        Place entity = new Place(insertDTO);
        entity = repo.save(entity);
        return new PlaceDTO(entity);
    }

    public void delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public PlaceDTO update(Long id, PlaceUpdateDTO updateDTO) {
        try {
            Place entity = repo.getOne(id);

            entity.setName(updateDTO.getName());
            entity.setEvents(updateDTO.getEvents());

            entity = repo.save(entity);
            return new PlaceDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }
    

}
