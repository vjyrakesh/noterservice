package com.rkasibhatla.noterservice.service;

import com.rkasibhatla.noterservice.entity.Note;
import com.rkasibhatla.noterservice.entity.Tag;
import com.rkasibhatla.noterservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Set<Note> getNotesOfTag(String name) {
        Tag foundTag = tagRepository.findTagByName(name);
        if(foundTag != null) {
            return foundTag.getNotes();
        } else
            return null;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }
}
