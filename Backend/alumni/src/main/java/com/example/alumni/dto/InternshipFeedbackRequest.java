package com.example.alumni.dto;

import lombok.Data;

/**
 * DTO for the request body to submit internship feedback for an alumnus.
 */
@Data
public class InternshipFeedbackRequest {

    // The JSON string representing the structured feedback.
    // e.g., {"technical_skills": 5, "comments": "Excellent work."}
    private String feedbackContent;

    // The ID of the company providing the feedback.
    private String companyId;
}