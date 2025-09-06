package com.example.alumni.repository;

import com.example.alumni.entity.CourseProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the CourseProvider entity.
 */
@Repository
public interface CourseProviderRepository extends JpaRepository<CourseProvider, String> {
    // Standard JPA methods are sufficient for now.
}
