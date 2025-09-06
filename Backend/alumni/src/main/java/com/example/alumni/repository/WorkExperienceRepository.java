package com.example.alumni.repository;

import com.example.alumni.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the WorkExperience entity.
 */
@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, String> {
    // This repository will be used to find a work experience record by its primary key (experienceId)
    // before it can be verified by an employer.
}