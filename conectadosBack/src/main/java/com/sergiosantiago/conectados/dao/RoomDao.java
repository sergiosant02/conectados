package com.sergiosantiago.conectados.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.sergiosantiago.conectados.models.Room;

@Component
public class RoomDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(Room room) {
        em.persist(room);
    }

    public List<Room> findAll() {
        return em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
    }

}
