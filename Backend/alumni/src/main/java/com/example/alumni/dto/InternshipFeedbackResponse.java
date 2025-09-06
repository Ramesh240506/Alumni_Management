package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO for returning details of a submitted InternshipFeedback record.
 */
@Data
@Builder
public class InternshipFeedbackResponse {
    private String feedbackId;
    private String internName;
    private String companyName;
    private String feedbackContent;
    private String reviewerName; // The name of the employer who submitted the feedback
    private LocalDateTime createdAt;
}