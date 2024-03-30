package com.sergiosantiago.conectados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergiosantiago.conectados.models.ShoppingItem;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

    List<ShoppingItem> findAll();

}
