package com.example.alumni.repository;

import com.example.alumni.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, String> {

    // Custom query to find all job postings with an 'OPEN' status
    List<JobPosting> findByStatusOrderByCreatedAtDesc(com.example.alumni.entity.enums.JobStatus status);
}