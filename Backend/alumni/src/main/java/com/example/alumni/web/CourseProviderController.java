package com.example.alumni.web;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;
import com.example.alumni.service.ProfessionalCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for endpoints related to Course Providers.
 */
@RestController
@RequestMapping("/provider")
@RequiredArgsConstructor
public class CourseProviderController {

    private final ProfessionalCourseService professionalCourseService;

    /**
     * API for a provider to add a completed course for an alumnus.
     * POST /provider/courses
     */
    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> addCompletedCourseForAlumni(@RequestBody CourseRequest courseRequest) {
        CourseResponse response = professionalCourseService.addCourseForAlumni(courseRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * API for a provider to submit a course verification.
     * POST /provider/verify-course/{courseId}
     */
    @PostMapping("/verify-course/{courseId}")
    public ResponseEntity<CourseResponse> submitCourseVerification(@PathVariable UUID courseId) {
        CourseResponse response = professionalCourseService.verifyCourse(courseId);
        return ResponseEntity.ok(response);
    }
}