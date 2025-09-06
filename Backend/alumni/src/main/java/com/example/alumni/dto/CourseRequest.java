package com.example.alumni.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// The unnecessary 'import java.util.UUID;' has been removed.

/**
 * DTO for the request body when a Course Provider adds a completed course for an alumnus.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

    // THE FIX IS HERE: The type has been changed from UUID to String
    // to match your User and AlumniProfile entity's primary key.
    private String alumniUserId;

    private String courseName;

    // Added this field to match the 'completion_date' field in your ProfessionalCourse entity.
    private LocalDate completionDate;
}