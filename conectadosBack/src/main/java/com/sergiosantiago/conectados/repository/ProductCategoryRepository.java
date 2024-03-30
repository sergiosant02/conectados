package com.sergiosantiago.conectados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sergiosantiago.conectados.models.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT p FROM ProductCategory p WHERE p.name LIKE %?1%")
    List<ProductCategory> findLikeByName(String name);

    @Query("SELECT p FROM ProductCategory p WHERE p.name = ?1")
    List<ProductCategory> findByName(String name);

}
