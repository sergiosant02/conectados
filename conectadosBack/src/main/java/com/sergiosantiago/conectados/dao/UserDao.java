package com.sergiosantiago.conectados.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.sergiosantiago.conectados.models.User;

@Component
public class UserDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void persist(User user) {
        em.persist(user);
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
