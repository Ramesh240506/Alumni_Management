package com.example.alumni.repository;

import com.example.alumni.entity.AlumniProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Import List

@Repository
public interface AlumniProfileRepository extends JpaRepository<AlumniProfile, String> {
    
    // --- ADD THIS NEW METHOD ---
    /**
     * Finds all alumni profiles associated with a specific college, excluding the
     * user making the request.
     * @param collegeId The ID of the college.
     * @param currentUserId The ID of the user to exclude from the results.
     * @return A list of alumni profiles.
     */
    List<AlumniProfile> findByCollege_CollegeIdAndAlumniUserIdNot(String collegeId, String currentUserId);
}