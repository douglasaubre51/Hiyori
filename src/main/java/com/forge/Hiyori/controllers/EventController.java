package com.forge.Hiyori.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.Hiyori.dtos.EventDto;
import com.forge.Hiyori.entities.Event;
import com.forge.Hiyori.repos.EventRepository;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

record MessageDto(String msg, Object data) {
}

@RestController
@RequestMapping("api/events")
public class EventController {

    private final EventRepository _eventRepo;
    private final ModelMapper _mapper;

    public EventController(
            EventRepository eventRepo,
            ModelMapper mapper) {
        _eventRepo = eventRepo;
        _mapper = mapper;
    }

    @GetMapping("all")
    public ResponseEntity<MessageDto> getAll() {
        try {
            List<Event> dbEventList = _eventRepo.findAll();

            return dbEventList.size() > 0 ? ResponseEntity.status(200).body(new MessageDto(
                    "success", dbEventList))
                    : ResponseEntity.status(400).body(new MessageDto(
                            "failure:empty list", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageDto> getById(@PathVariable long id) {
        try {
            Event dbEvent = _eventRepo.findById(id).orElse(null);

            return dbEvent != null ? ResponseEntity.status(200).body(new MessageDto("success", dbEvent))
                    : ResponseEntity.status(400).body(new MessageDto("failure:object not present", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<MessageDto> post(@RequestBody EventDto dto) {
        try {
            Event newEvent = new Event();
            newEvent = _mapper.map(dto, Event.class);
            Event dbEvent = _eventRepo.save(newEvent);

            return ResponseEntity.status(201).body(new MessageDto("success", dbEvent));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageDto> update(
            @PathVariable long id,
            @RequestBody EventDto dto) {
        try {
            if (_eventRepo.existsById(id) == false)
                return ResponseEntity.status(400)
                        .body(new MessageDto("failure:object not present", null));

            Event dbEvent = _eventRepo.findById(id).get();
            dbEvent.setTitle(dto.getTitle());
            dbEvent.setAuthor(dto.getAuthor());
            dbEvent.setShortDesc(dto.getShortDesc());
            dbEvent.setDetails(dto.getDetails());
            dbEvent.setEventDate(dto.getEventDate());
            dbEvent.setEventTime(dto.getEventTime());
            dbEvent.setThumbnail(dto.getThumbnail());
            dbEvent.setTags(dto.getTags());

            Event newDbEvent = _eventRepo.save(dbEvent);
            return ResponseEntity.ok(new MessageDto("updated", newDbEvent));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MessageDto> deleteById(@PathVariable long id) {
        try {
            _eventRepo.deleteById(id);
            return ResponseEntity.ok(new MessageDto("success", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }
}
