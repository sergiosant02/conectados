package com.sergiosantiago.conectados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sergiosantiago.conectados.models.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAll();

    @Query("SELECT note FROM Note note WHERE note.writeIn.id = ?1")
    List<Note> findByRoomId(Long roomId);
}
