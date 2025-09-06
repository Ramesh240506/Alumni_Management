package com.example.alumni.repository;

import com.example.alumni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> { // <-- CORRECT ID TYPE

    Optional<User> findByEmail(String email);
}