package com.example.alumni.repository;

import com.example.alumni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    // A common method needed for authentication and lookups
    Optional<User> findByEmail(String email);
}