package com.example.alumni.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alumni.dto.ApiResponse;
import com.example.alumni.dto.WorkExperienceRequest;
import com.example.alumni.service.WorkExperienceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/work-experience")
@RequiredArgsConstructor
public class WorkExperienceRequestController {
    
    private final WorkExperienceService workExperienceService;
    
    @PostMapping("/request-verification")
    public ResponseEntity<ApiResponse> requestVerification(@Valid @RequestBody WorkExperienceRequest request) {
        // Requires ALUMNI role
        workExperienceService.requestWorkExperienceVerification(request);
        return ResponseEntity.ok(new ApiResponse(true, "Work experience verification request sent successfully."));
    }
}