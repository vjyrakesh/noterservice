package com.rkasibhatla.noterservice.service;

import com.rkasibhatla.noterservice.entity.Note;
import com.rkasibhatla.noterservice.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public List<Note> getAllNotes() {
        return notesRepository.findAll();
    }

    public Note getNoteById(Integer id) {
        Optional<Note> note = notesRepository.findById(id);
        if(note.isPresent())
            return note.get();
        else
            return null;
    }

    public Note addNote(Note note) {
        Date now = new Date();
        note.setCreatedAt(now);
        note.setUpdatedAt(now);
        Note savedNote = notesRepository.save(note);
        return savedNote;
    }

    public void deleteNote(Integer id) {
        notesRepository.delete(notesRepository.getById(id));
    }

    public Note updateNote(Integer id, Note note) {
        Note foundNote = notesRepository.getById(id);
        if(foundNote != null) {
            foundNote.setTitle(note.getTitle());
            foundNote.setDescription(note.getDescription());
            foundNote.setUpdatedAt(new Date());
            return notesRepository.save(foundNote);
        } else {
            return null;
        }
    }
}
