package com.example.alumni.service;

import com.example.alumni.dto.WorkExperienceRequest;
import com.example.alumni.dto.WorkExperienceResponse;

public interface WorkExperienceService {

    WorkExperienceResponse createWorkExperience(WorkExperienceRequest request);

    /**
     * Verifies a work experience record.
     *
     * @param experienceId The String ID of the work experience record to verify.
     * @return A DTO representing the updated, verified record.
     */
    WorkExperienceResponse verifyExperience(String experienceId); // <-- FIX: Changed from UUID to String
}