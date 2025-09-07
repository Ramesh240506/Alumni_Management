package com.example.alumni.web;

import com.example.alumni.dto.ApiResponse;
import com.example.alumni.serviceimpl.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employer")
@RequiredArgsConstructor
public class EmployerController {

    // Injecting Impl directly for simplicity in this example.
    // In a real app, you'd inject the CompanyService interface.
    private final CompanyServiceImpl companyService;

    // Example endpoint for an employer to approve a work experience record
    @PostMapping("/work-experience/{experienceId}/approve")
    public ResponseEntity<ApiResponse> approveWorkExperience(@PathVariable String experienceId) {
        // Requires EMPLOYER_REP role
        companyService.approveWorkExperience(experienceId);
        return ResponseEntity.ok(new ApiResponse(true, "Work experience verified. Awaiting college approval."));
    }
}