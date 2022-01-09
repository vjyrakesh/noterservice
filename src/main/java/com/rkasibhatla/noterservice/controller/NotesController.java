package com.rkasibhatla.noterservice.controller;

import com.rkasibhatla.noterservice.entity.Note;
import com.rkasibhatla.noterservice.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @GetMapping
    public List<Note> getAllNotes() {
        return notesService.getAllNotes();
    }

    @PostMapping
    public ResponseEntity<?> addNote(@RequestBody Note note) {
        Note savedNote = notesService.addNote(note);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedNote.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneNote(@PathVariable Integer id) {
        Note foundNote = notesService.getNoteById(id);
        if(foundNote != null)
            return new ResponseEntity<>(foundNote, HttpStatus.OK);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOneNote(Integer id) {
        notesService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOneNote(@PathVariable Integer id, @RequestBody Note note) {
        Note updatedNote = notesService.updateNote(id, note);
        if(updatedNote != null)
            return ResponseEntity.ok(updatedNote);
        else
            return ResponseEntity.notFound().build();
    }

}
