package com.forge.Hiyori.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.Hiyori.dtos.EventDto;
import com.forge.Hiyori.entities.Event;
import com.forge.Hiyori.repos.EventRepository;

import javax.swing.text.html.parser.Entity;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api/event")
public class EventController {

    private final EventRepository _eventRepo;
    private final ModelMapper _mapper;

    public EventController(
        EventRepository eventRepo,
        ModelMapper mapper) {
        _eventRepo = eventRepo;
        _mapper = mapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> getById(@PathVariable long id) {
        try {
            Event dbEvent = _eventRepo.findById(id).orElse(null);

            return 
                dbEvent != null ? ResponseEntity.status(200).body(dbEvent) : ResponseEntity.status(400).body(new Event());

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(500)
                    .body(new Event());
        }
    }

    @PostMapping("")
    public ResponseEntity<Event> post(@RequestBody EventDto dto) {
        try{
            Event newEvent = new Event();
            newEvent = _mapper.map(dto,Event.class);
            Event dbEvent = _eventRepo.save(newEvent);

            return ResponseEntity.status(201).body(dbEvent);
        }
        catch(Exception e){
            e.printStackTrace();

            return ResponseEntity.status(500)
            .body(new Event());
        }
    }

}
