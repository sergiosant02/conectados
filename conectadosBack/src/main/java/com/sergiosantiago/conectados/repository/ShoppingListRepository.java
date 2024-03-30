package com.sergiosantiago.conectados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergiosantiago.conectados.models.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    List<ShoppingList> findAll();
}
