package com.example.alumni.service;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;

/**
 * Service interface for managing ProfessionalCourse records.
 */
public interface ProfessionalCourseService {

    /**
     * Allows a provider to add a course for an alumnus.
     *
     * @param request    The DTO with course details.
     * @param providerId The String ID of the provider adding the course.
     * @return A DTO of the newly created course record.
     */
    CourseResponse addCourseForAlumni(CourseRequest request, String providerId);

    /**
     * Allows a provider to verify a course.
     *
     * @param courseId The String ID of the course to verify. // <-- THE FIX IS HERE
     * @return A DTO of the updated course record.
     */
    CourseResponse verifyCourse(String courseId); // <-- THE FIX IS HERE (Changed from UUID to String)
}