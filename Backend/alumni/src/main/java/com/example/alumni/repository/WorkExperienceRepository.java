package com.example.alumni.repository;

import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, String> {
    
    /**
     * Finds all work experience records associated with a specific company that have a specific status.
     * Spring Data JPA creates the query automatically from the method name.
     *
     * @param companyId The ID of the company.
     * @param status The status of the records to find.
     * @return A list of matching work experience records.
     */
    List<WorkExperience> findByCompany_CompanyIdAndStatus(String companyId, RecordStatus status);

    /**
     * Finds all work experience records for alumni of a specific college that have a specific status.
     * This is needed for the CollegeServiceImpl.
     *
     * @param collegeId The ID of the college.
     * @param status The status of the records to find.
     * @return A list of matching work experience records.
     */
    List<WorkExperience> findByAlumniProfile_College_CollegeIdAndStatus(String collegeId, RecordStatus status);
}