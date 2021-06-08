package com.poo2021.ac2aflab.controller;

import java.net.URI;
import java.util.List;

import com.poo2021.ac2aflab.dto.Event.EventDTO;
import com.poo2021.ac2aflab.dto.Event.EventInsertDTO;
import com.poo2021.ac2aflab.dto.Event.EventUpdateDTO;
import com.poo2021.ac2aflab.dto.Place.PlaceDTO;
import com.poo2021.ac2aflab.dto.Ticket.TicketDTO;
import com.poo2021.ac2aflab.services.EventService;
import com.poo2021.ac2aflab.services.PlaceService;
import com.poo2021.ac2aflab.services.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService eventService;

    @Autowired 
    private TicketService ticketService;

    @Autowired
    private PlaceService placeService;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getEvents(
        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "name",         defaultValue = "") String name,
        @RequestParam(value = "description",      defaultValue = "") String description
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        
        Page<EventDTO> list = eventService.getEvents(pageRequest, name.trim(), description.trim());

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO dto = eventService.getEventById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    @GetMapping("{id}/tickets")
    public List<TicketDTO> getTicketsEvent(@PathVariable Long id) {
        EventDTO dto = eventService.getEventById(id);
        return ticketService.toDTOList(dto.getTickets());
    }

    @PostMapping
	public ResponseEntity<EventDTO> insert(@RequestBody EventInsertDTO insertDto){
		EventDTO dto = eventService.insert(insertDto); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @PostMapping("{eventId}/places/{placeId}")
    public ResponseEntity<EventDTO> insertPlace(@PathVariable Long eventId, @PathVariable Long placeId) {
        EventDTO dto = eventService.insertPlace(eventId, placeId);
        PlaceDTO place = placeService.getPlaceById(placeId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{eventId}/places/{placeId}").buildAndExpand(dto.getId(), place.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		eventService.delete(id); 
		return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
	public ResponseEntity<EventDTO> update(@RequestBody EventUpdateDTO updateDto, @PathVariable Long id){
		EventDTO dto = eventService.update(id, updateDto); 
		return ResponseEntity.ok().body(dto);
	}

}
