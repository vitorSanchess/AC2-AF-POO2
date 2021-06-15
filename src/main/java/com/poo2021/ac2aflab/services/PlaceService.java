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
    private PlaceRepository placeRepo;

    public Page<PlaceDTO> getPlaces(PageRequest pageRequest, String name, String address) {
        Page<Place> list = placeRepo.find(pageRequest, name, address);
        return list.map( a -> new PlaceDTO(a));
    }

    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> op = placeRepo.findById(id);
        Place Place = op.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found"));
        return new PlaceDTO(Place);
    }

    public PlaceDTO insert(PlaceInsertDTO insertDTO) {
        Place entity = new Place(insertDTO);
        entity = placeRepo.save(entity);
        return new PlaceDTO(entity);
    }

    public void delete(Long id) {
        if(!placeRepo.findById(id).get().getEvents().isEmpty())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't delete a place that has been used");
        try {
            placeRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }

    public PlaceDTO update(Long id, PlaceUpdateDTO updateDTO) {
        try {
            Place entity = placeRepo.getOne(id);

            entity.setName(updateDTO.getName());
            entity.setEvents(updateDTO.getEvents());

            entity = placeRepo.save(entity);
            return new PlaceDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        }
    }
    

}
