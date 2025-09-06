package com.example.alumni.repository;

import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, String> {
    List<WorkExperience> findByStatus(RecordStatus status);
}