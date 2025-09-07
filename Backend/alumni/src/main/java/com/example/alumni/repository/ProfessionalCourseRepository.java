package com.example.alumni.repository;

import com.example.alumni.entity.ProfessionalCourse;
import com.example.alumni.entity.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalCourseRepository extends JpaRepository<ProfessionalCourse, String> {

    // --- ADD THIS NEW METHOD ---
    /**
     * Finds all professional course records associated with a specific provider
     * that have a specific status.
     * @param providerId The ID of the course provider.
     * @param status The status of the records to find (e.g., PENDING_AUTHORITY).
     * @return A list of matching professional course records.
     */
    List<ProfessionalCourse> findByCourseProvider_ProviderIdAndStatus(String providerId, RecordStatus status);
}