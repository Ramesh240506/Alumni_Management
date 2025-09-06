package com.example.alumni.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for the request body when a Course Provider adds a completed course for an alumnus.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    // The ID of the alumnus for whom the course is being added.
    private UUID alumniUserId;

    // The name of the course or certification.
    private String courseName;

    // The provider's ID would be inferred from the authenticated user in a real scenario.
    // We can add it here if the API requires it explicitly.
    // private UUID providerId;
}