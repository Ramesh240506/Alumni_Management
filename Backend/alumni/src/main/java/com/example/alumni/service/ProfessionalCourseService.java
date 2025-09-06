package com.example.alumni.service;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;
import java.util.UUID;

/**
 * Service interface defining business logic for managing ProfessionalCourse records.
 */
public interface ProfessionalCourseService {

    /**
     * Allows a course provider to add a completed course record for an alumnus.
     * The record is typically created with a VERIFIED status since the provider is the source of truth.
     *
     * @param request The DTO containing the course and alumni details.
     * @return A DTO representing the newly created course record.
     */
    CourseResponse addCourseForAlumni(CourseRequest request);

    /**
     * Allows a course provider to verify a course record that was requested by an alumnus.
     *
     * @param courseId The UUID of the course record to verify.
     * @return A DTO representing the updated, verified course record.
     */
    CourseResponse verifyCourse(UUID courseId);
}