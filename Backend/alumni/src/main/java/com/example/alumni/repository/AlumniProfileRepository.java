package com.example.alumni.repository;

import com.example.alumni.entity.AlumniProfile;
import com.example.alumni.entity.enums.VerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumniProfileRepository extends JpaRepository<AlumniProfile, String> {
    List<AlumniProfile> findByVerificationStatus(VerificationStatus status);
}