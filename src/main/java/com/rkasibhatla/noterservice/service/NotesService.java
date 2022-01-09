package com.rkasibhatla.noterservice.service;

import com.rkasibhatla.noterservice.entity.Note;
import com.rkasibhatla.noterservice.entity.Tag;
import com.rkasibhatla.noterservice.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private TagService tagService;

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
        Set<Tag> tagEntities = new HashSet<>();
        for(Tag oneGivenTag: note.getTags()) {
            Tag foundTag = tagService.getTagByName(oneGivenTag.getName());
            if(foundTag != null)
                tagEntities.add(foundTag);
            else
                tagEntities.add(tagService.addTag(oneGivenTag));
        }
        note.setTags(tagEntities);
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
