package com.rkasibhatla.noterservice.controller;

import com.rkasibhatla.noterservice.dto.NoteDto;
import com.rkasibhatla.noterservice.entity.Note;
import com.rkasibhatla.noterservice.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @GetMapping
    public List<NoteDto> getAllNotes(Principal principal) {
        List<Note> notes = notesService.getAllNotes();
        List<NoteDto> noteDtoList = new ArrayList<>();
        notes.forEach(note -> {
            NoteDto noteDto = new NoteDto();
            noteDto.setId(note.getId());
            noteDto.setTitle(note.getTitle());
            noteDto.setDescription(note.getDescription());
            noteDto.setCreatedAt(note.getCreatedAt());
            noteDto.setUpdatedAt(note.getUpdatedAt());
            List<String> tagList = new ArrayList<>();
            note.getTags().forEach(tag -> tagList.add(tag.getName()));
            noteDto.setTags(tagList);
            noteDtoList.add(noteDto);
        });
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + authentication.getName());
        System.out.println("Logged in principal: " + principal.getName());
        return noteDtoList;
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
        if(foundNote != null) {
            NoteDto noteDto = new NoteDto();
            noteDto.setId(foundNote.getId());
            noteDto.setTitle(foundNote.getTitle());
            noteDto.setDescription(foundNote.getDescription());
            noteDto.setCreatedAt(foundNote.getCreatedAt());
            noteDto.setUpdatedAt(foundNote.getUpdatedAt());
            List<String> tagList = new ArrayList<>();
            foundNote.getTags().forEach(tag -> tagList.add(tag.getName()));
            noteDto.setTags(tagList);
            return new ResponseEntity<>(noteDto, HttpStatus.OK);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOneNote(@PathVariable Integer id) {
        notesService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOneNote(@PathVariable Integer id, @RequestBody NoteDto note) {
        Note updatedNote = notesService.updateNote(id, note);
        if(updatedNote != null) {
            NoteDto noteDto = new NoteDto();
            noteDto.setId(updatedNote.getId());
            noteDto.setTitle(updatedNote.getTitle());
            noteDto.setDescription(updatedNote.getDescription());
            noteDto.setCreatedAt(updatedNote.getCreatedAt());
            noteDto.setUpdatedAt(updatedNote.getUpdatedAt());
            List<String> tagList = new ArrayList<>();
            updatedNote.getTags().forEach(tag -> tagList.add(tag.getName()));
            noteDto.setTags(tagList);
            return new ResponseEntity<>(noteDto, HttpStatus.OK);
        }
        else
            return ResponseEntity.notFound().build();
    }

}
