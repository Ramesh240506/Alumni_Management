package com.example.alumni.service;

import com.example.alumni.dto.InternshipFeedbackRequest;
import com.example.alumni.dto.InternshipFeedbackResponse;

/**
 * Service interface for business logic related to InternshipFeedback.
 */
public interface InternshipFeedbackService {

    /**
     * Allows an employer to submit feedback for an intern.
     *
     * @param request      The DTO containing feedback details.
     * @param internUserId The ID of the alumnus (intern) receiving the feedback.
     * @param reviewerId   The ID of the authenticated user (employer) submitting the feedback.
     * @return A DTO representing the successfully submitted feedback.
     */
    InternshipFeedbackResponse submitFeedback(InternshipFeedbackRequest request, String internUserId, String reviewerId);
}