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

import com.forge.Hiyori.dtos.GameDto;
import com.forge.Hiyori.entities.Game;
import com.forge.Hiyori.repos.GameRepository;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository _eventRepo;
    private final ModelMapper _mapper;

    public GameController(
                GameRepository eventRepo,
                ModelMapper mapper) {
            _eventRepo = eventRepo;
            _mapper = mapper;
        }

    @GetMapping("all")
    public ResponseEntity<MessageDto> getAll() {
        try {
            List<Game> dbGameList = _eventRepo.findAll();

            return dbGameList.size() > 0 ? ResponseEntity.status(200).body(new MessageDto(
                    "success", dbGameList))
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
            Game dbGame = _eventRepo.findById(id).orElse(null);

            return dbGame != null ? ResponseEntity.status(200).body(new MessageDto("success", dbGame))
                    : ResponseEntity.status(400).body(new MessageDto("failure:object not present", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<MessageDto> post(@RequestBody Game dto) {
        try {
            Game newGame = new Game();
            newGame = _mapper.map(dto, Game.class);
            @SuppressWarnings("null")
            Game dbGame = _eventRepo.save(newGame);

            return ResponseEntity.status(201).body(new MessageDto("success", dbGame));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageDto> update(
            @PathVariable long id,
            @RequestBody GameDto dto) {
        try {
            if (_eventRepo.existsById(id) == false)
                return ResponseEntity.status(400)
                        .body(new MessageDto("failure:object not present", null));

            Game dbGame = _eventRepo.findById(id).get();
            dbGame.setTitle(dto.getTitle());
            dbGame.setAuthor(dto.getAuthor());
            dbGame.setDetails(dto.getDetails());
            dbGame.setThumbnail(dto.getThumbnail());
            dbGame.setTags(dto.getTags());
            dbGame.setSourceURI(dto.getSourceURI());

            Game newDbGame = _eventRepo.save(dbGame);
            return ResponseEntity.ok(new MessageDto("updated", newDbGame));

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
