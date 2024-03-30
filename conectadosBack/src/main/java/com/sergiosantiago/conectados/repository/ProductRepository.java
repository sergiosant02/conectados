package com.sergiosantiago.conectados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sergiosantiago.conectados.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findLikeByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    List<Product> findByName(String name);
}