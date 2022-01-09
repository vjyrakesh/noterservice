package com.rkasibhatla.noterservice.controller;

import com.rkasibhatla.noterservice.entity.Note;
import com.rkasibhatla.noterservice.entity.Tag;
import com.rkasibhatla.noterservice.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{name}/notes")
    public ResponseEntity<?> getNotesOfTag(@PathVariable String name) {
        Set<Note> notes = tagService.getNotesOfTag(name);
        if(notes != null) {
            return ResponseEntity.ok(notes);
        } else
            return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity addTag(@RequestBody Tag tag) {
        Tag addedTag = tagService.addTag(tag);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addedTag.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
