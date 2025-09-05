package com.example.alumni.repository;

import com.example.alumni.entity.DigitalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DigitalIdRepository extends JpaRepository<DigitalId, String> {

    // Custom query to find a digital ID by the alumnus's user ID
    Optional<DigitalId> findByAlumniProfile_AlumniUserId(String alumniUserId);
}