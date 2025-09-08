package com.example.alumni.service;

import java.util.List;

import com.example.alumni.dto.WorkExperienceResponse;

public interface CompanyService {
    /**
     * Retrieves a list of pending work experience verifications for the authenticated employer.
     * @return A list of work experience records awaiting verification.
     */
    List<WorkExperienceResponse> getPendingVerifications();

    /**
     * Approves a pending work experience verification request.
     * Changes the record status from PENDING_AUTHORITY to PENDING_COLLEGE.
     * @param experienceId The ID of the work experience record to approve.
     */
    void approveWorkExperience(String experienceId);

    /**
     * Rejects a pending work experience verification request.
     * Changes the record status to REJECTED.
     * @param experienceId The ID of the work experience record to reject.
     */
    void rejectWorkExperience(String experienceId);
}