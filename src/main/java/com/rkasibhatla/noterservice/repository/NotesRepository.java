package com.rkasibhatla.noterservice.repository;

import com.rkasibhatla.noterservice.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Note, Integer> {
}
