package com.example.alumni.repository;

import com.example.alumni.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the College entity.
 */
@Repository
public interface CollegeRepository extends JpaRepository<College, String> {

    /**
     * Finds a college by its name.
     * Spring Data JPA automatically implements this method based on its name.
     *
     * @param name The name of the college to find.
     * @return An Optional containing the found college, or empty if not found.
     */
    Optional<College> findByName(String name);

}