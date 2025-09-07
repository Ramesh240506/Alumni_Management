package com.example.alumni.repository;

import com.example.alumni.entity.InternshipFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipFeedbackRepository extends JpaRepository<InternshipFeedback, String> {
}