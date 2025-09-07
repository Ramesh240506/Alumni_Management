package com.example.alumni.service;

import com.example.alumni.dto.CourseResponse;

import java.util.List;

public interface CourseProviderService {

    /**
     * Retrieves a list of pending verification requests for the authenticated course provider.
     * @return A list of course records awaiting verification.
     */
    List<CourseResponse> getPendingVerifications();

    /**
     * Approves a pending course verification request.
     * Changes the record status from PENDING_AUTHORITY to PENDING_COLLEGE.
     * @param courseRecordId The ID of the professional course record to approve.
     */
    void approveVerification(String courseRecordId);

    /**
     * Rejects a pending course verification request.
     * Changes the record status to REJECTED.
     * @param courseRecordId The ID of the professional course record to reject.
     */
    void rejectVerification(String courseRecordId);
}