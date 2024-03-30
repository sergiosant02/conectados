package com.sergiosantiago.conectados.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sergiosantiago.conectados.models.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.code =?1")
    public Optional<Room> findByCode(String code);

}
