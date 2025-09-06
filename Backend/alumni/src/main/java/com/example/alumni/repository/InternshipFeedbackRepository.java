package com.example.alumni.repository;

import com.example.alumni.entity.InternshipFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InternshipFeedback entity.
 */
@Repository
public interface InternshipFeedbackRepository extends JpaRepository<InternshipFeedback, String> {
    // Standard JPA methods will be used by the service
}