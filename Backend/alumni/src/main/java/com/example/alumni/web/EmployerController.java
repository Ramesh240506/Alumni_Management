package com.example.alumni.web;

import com.example.alumni.dto.InternshipFeedbackRequest;
import com.example.alumni.dto.InternshipFeedbackResponse;
import com.example.alumni.dto.WorkExperienceResponse;
import com.example.alumni.service.InternshipFeedbackService;
import com.example.alumni.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for endpoints related to Employers.
 * This controller handles tasks assigned to Rathidharani.
 */
@RestController
@RequestMapping("/employer")
@RequiredArgsConstructor
public class EmployerController {

    // These are the services required for YOUR assigned APIs.
    private final WorkExperienceService workExperienceService;
    private final InternshipFeedbackService internshipFeedbackService;

    /**
     * API to verify an alumnus's work experience.
     * POST /employer/verify-experience/{expId}
     */
    @PostMapping("/verify-experience/{expId}")
    public ResponseEntity<WorkExperienceResponse> verifyAlumniWorkExperience(@PathVariable String expId) {
        WorkExperienceResponse response = workExperienceService.verifyExperience(expId);
        return ResponseEntity.ok(response);
    }

    /**
     * API to submit internship feedback for an alumnus.
     * POST /employer/intern-feedback/{alumniId}
     */
    @PostMapping("/intern-feedback/{alumniId}")
    public ResponseEntity<InternshipFeedbackResponse> submitInternshipFeedback(
            @PathVariable String alumniId,
            @RequestBody InternshipFeedbackRequest feedbackRequest) {
        // In a real application, the reviewer's ID would come from the security context.
        String reviewerId = "replace-with-authenticated-employer-id"; // Placeholder

        InternshipFeedbackResponse submittedFeedback = internshipFeedbackService.submitFeedback(feedbackRequest, alumniId, reviewerId);
        return new ResponseEntity<>(submittedFeedback, HttpStatus.CREATED);
    }
}