package com.example.alumni.service;

import com.example.alumni.dto.WorkExperienceResponse; // <-- UPDATED IMPORT
import java.util.List;

public interface CollegeService {
    
    /**
     * Retrieves a list of pending verification requests for the authenticated college admin.
     * In this implementation, it focuses on WorkExperience records.
     * @return A list of WorkExperienceResponse DTOs.
     */
    List<WorkExperienceResponse> getPendingVerifications(); // <-- CORRECTED RETURN TYPE

    /**
     * Approves a pending record.
     * @param recordId The ID of the record to approve.
     */
    void approveRecord(String recordId);

    /**
     * Rejects a pending record.
     * @param recordId The ID of the record to reject.
     */
    void rejectRecord(String recordId);
}