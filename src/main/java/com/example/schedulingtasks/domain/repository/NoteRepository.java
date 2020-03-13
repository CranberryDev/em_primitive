package com.example.schedulingtasks.domain.repository;

import com.example.schedulingtasks.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
