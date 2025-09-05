// CORRECTED CODE:
package com.example.alumni.repository;

import com.example.alumni.entity.AlumniProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumniProfileRepository extends JpaRepository<AlumniProfile, String> { // <-- CORRECT ID TYPE
    // Spring Data JPA provides the implementation for findById(String id) automatically.
}