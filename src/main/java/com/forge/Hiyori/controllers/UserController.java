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

import com.forge.Hiyori.dtos.UserDto;
import com.forge.Hiyori.entities.User;
import com.forge.Hiyori.repos.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository _eventRepo;
    private final ModelMapper _mapper;

    public UserController(
            UserRepository eventRepo,
            ModelMapper mapper) {
        _eventRepo = eventRepo;
        _mapper = mapper;
    }

    @GetMapping("all")
    public ResponseEntity<MessageDto> getAll() {
        try {
            List<User> dbUserList = _eventRepo.findAll();

            return dbUserList.size() > 0 ? ResponseEntity.status(200).body(new MessageDto(
                    "success", dbUserList))
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
            User dbUser = _eventRepo.findById(id).orElse(null);

            return dbUser != null ? ResponseEntity.status(200).body(new MessageDto("success", dbUser))
                    : ResponseEntity.status(400).body(new MessageDto("failure:object not present", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<MessageDto> post(@RequestBody UserDto dto) {
        try {
            User newUser = new User();
            newUser = _mapper.map(dto, User.class);
            @SuppressWarnings("null")
            User dbUser = _eventRepo.save(newUser);

            return ResponseEntity.status(201).body(new MessageDto("success", dbUser));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageDto> update(
            @PathVariable long id,
            @RequestBody UserDto dto) {
        try {
            if (_eventRepo.existsById(id) == false)
                return ResponseEntity.status(400)
                        .body(new MessageDto("failure:object not present", null));

            User dbUser = _eventRepo.findById(id).get();
            dbUser.setFirstName(dto.getFirstName());
            dbUser.setLastName(dto.getLastName());
            dbUser.setProfile(dto.getProfile());
            dbUser.setEmail(dto.getEmail());
            dbUser.setPassword(dto.getPassword());
            dbUser.setAdmin(dto.isAdmin());

            User newDbUser = _eventRepo.save(dbUser);
            return ResponseEntity.ok(new MessageDto("updated", newDbUser));

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
