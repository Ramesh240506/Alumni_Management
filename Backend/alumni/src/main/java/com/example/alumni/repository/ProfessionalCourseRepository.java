package com.example.alumni.repository;

import com.example.alumni.entity.ProfessionalCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the ProfessionalCourse entity.
 */
@Repository
public interface ProfessionalCourseRepository extends JpaRepository<ProfessionalCourse, String> {
    // This will be used by the CourseProviderService to find and update course records.
}