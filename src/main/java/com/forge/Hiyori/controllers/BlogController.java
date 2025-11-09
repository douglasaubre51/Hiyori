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

import com.forge.Hiyori.dtos.BlogDto;
import com.forge.Hiyori.entities.Blog;
import com.forge.Hiyori.repos.BlogRepository;

record MessageDto(String msg, Object data) {
}

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogRepository _eventRepo;
    private final ModelMapper _mapper;

    public BlogController(
                BlogRepository eventRepo,
                ModelMapper mapper) {
            _eventRepo = eventRepo;
            _mapper = mapper;
        }

    @GetMapping("all")
    public ResponseEntity<MessageDto> getAll() {
        try {
            List<Blog> dbBlogList = _eventRepo.findAll();

            return dbBlogList.size() > 0 ? ResponseEntity.status(200).body(new MessageDto(
                    "success", dbBlogList))
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
            Blog dbBlog = _eventRepo.findById(id).orElse(null);

            return dbBlog != null ? ResponseEntity.status(200).body(new MessageDto("success", dbBlog))
                    : ResponseEntity.status(400).body(new MessageDto("failure:object not present", null));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<MessageDto> post(@RequestBody Blog dto) {
        try {
            Blog newBlog = new Blog();
            newBlog = _mapper.map(dto, Blog.class);
            @SuppressWarnings("null")
            Blog dbBlog = _eventRepo.save(newBlog);

            return ResponseEntity.status(201).body(new MessageDto("success", dbBlog));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new MessageDto("error:" + e.getMessage(), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageDto> update(
            @PathVariable long id,
            @RequestBody BlogDto dto) {
        try {
            if (_eventRepo.existsById(id) == false)
                return ResponseEntity.status(400)
                        .body(new MessageDto("failure:object not present", null));

            Blog dbBlog = _eventRepo.findById(id).get();
            dbBlog.setTitle(dto.getTitle());
            dbBlog.setAuthor(dto.getAuthor());
            dbBlog.setThumbnail(dto.getThumbnail());
            dbBlog.setTags(dto.getTags());
            dbBlog.setContent(dto.getContent());

            Blog newDbBlog = _eventRepo.save(dbBlog);
            return ResponseEntity.ok(new MessageDto("updated", newDbBlog));

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
