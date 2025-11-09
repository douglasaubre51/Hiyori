package com.forge.Hiyori.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forge.Hiyori.dtos.PictureDto;
import com.forge.Hiyori.entities.Picture;
import com.forge.Hiyori.repos.PictureRepository;

@RestController
@RequestMapping("/api/pictures")
public class PictureController {
    
    private final PictureRepository _eventRepo;
    private final ModelMapper _mapper;

    public PictureController(
                PictureRepository eventRepo,
                ModelMapper mapper) {
            _eventRepo = eventRepo;
            _mapper = mapper;
        }

    @GetMapping("all")
    public ResponseEntity<MessageDto> getAll() {
        try {
            List<Picture> dbPictureList = _eventRepo.findAll();

            return dbPictureList.size() > 0 ? ResponseEntity.status(200).body(new MessageDto(
                    "success", dbPictureList))
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
            Picture dbPicture = _eventRepo.findById(id).orElse(null);

            return dbPicture != null ? ResponseEntity.status(200).body(new MessageDto("success", dbPicture))
                    : ResponseEntity.status(400).body(new MessageDto("failure:object not present", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<MessageDto> post(@RequestBody Picture dto) {
        try {
            Picture newPicture = new Picture();
            newPicture = _mapper.map(dto, Picture.class);
            @SuppressWarnings("null")
            Picture dbPicture = _eventRepo.save(newPicture);

            return ResponseEntity.status(201).body(new MessageDto("success", dbPicture));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageDto> update(
            @PathVariable long id,
            @RequestBody PictureDto dto) {
        try {
            if (_eventRepo.existsById(id) == false)
                return ResponseEntity.status(400)
                        .body(new MessageDto("failure:object not present", null));

            Picture dbPicture = _eventRepo.findById(id).get();
            dbPicture.setTitle(dto.getTitle());
            dbPicture.setDetails(dto.getDetails());
            dbPicture.setTakenDate(dto.getTakenDate());
            dbPicture.setTags(dto.getTags());
            dbPicture.setImageURI(dto.getImageURI());

            Picture newDbPicture = _eventRepo.save(dbPicture);
            return ResponseEntity.ok(new MessageDto("updated", newDbPicture));

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
