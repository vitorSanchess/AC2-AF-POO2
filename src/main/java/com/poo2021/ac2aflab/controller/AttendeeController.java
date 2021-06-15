package com.poo2021.ac2aflab.controller;

import java.net.URI;

import com.poo2021.ac2aflab.dto.Attendee.AttendeeDTO;
import com.poo2021.ac2aflab.dto.Attendee.AttendeeInsertDTO;
import com.poo2021.ac2aflab.dto.Attendee.AttendeeUpdateDTO;
import com.poo2021.ac2aflab.services.AttendeeService;

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
@RequestMapping("/attendees")
public class AttendeeController {
    
    @Autowired
    private AttendeeService service;

    @GetMapping
    public ResponseEntity<Page<AttendeeDTO>> getAttendees(
        @RequestParam(value = "page",         defaultValue = "0") Integer page,
        @RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
        @RequestParam(value = "direction",    defaultValue = "ASC") String direction,
        @RequestParam(value = "orderBy",      defaultValue = "id") String orderBy,
        @RequestParam(value = "name",         defaultValue = "") String name,
        @RequestParam(value = "email",      defaultValue = "") String email,
        @RequestParam(value = "balance",      defaultValue = "0.0") Double balance
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);
        
        Page<AttendeeDTO> list = service.getAttendees(pageRequest, name.trim(), email.trim(), balance);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<AttendeeDTO> getAttendeeById(@PathVariable Long id) {
        AttendeeDTO dto = service.getAttendeeById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
	public ResponseEntity<AttendeeDTO> insert(@RequestBody AttendeeInsertDTO insertDto){
		AttendeeDTO dto = service.insert(insertDto); 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

    @DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id); 
		return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
	public ResponseEntity<AttendeeDTO> update(@RequestBody AttendeeUpdateDTO updateDto, @PathVariable Long id){
		AttendeeDTO dto = service.update(id, updateDto); 
		return ResponseEntity.ok().body(dto);
	}
}
