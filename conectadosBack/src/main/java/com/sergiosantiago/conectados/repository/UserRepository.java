package com.sergiosantiago.conectados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sergiosantiago.conectados.models.Product;
import com.sergiosantiago.conectados.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT count(u) FROM User u WEHRE u.email = ?1")
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    List<Product> findByName(String email);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role IS NOT NULL AND ?1 = u.role")
    Integer countByRole(String role);

}
