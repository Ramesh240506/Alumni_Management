package com.example.alumni.web;

import com.example.alumni.dto.AcademicRecordDto;
import com.example.alumni.dto.ApiResponse;
import com.example.alumni.service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/college-admin")
@RequiredArgsConstructor
public class CollegeAdminController {

    private final CollegeService collegeService;

    @GetMapping("/verifications/pending")
    public ResponseEntity<List<AcademicRecordDto>> getPendingVerifications() {
        // Requires COLLEGE_ADMIN role
        return ResponseEntity.ok(collegeService.getPendingVerifications());
    }

    @PostMapping("/verifications/{recordId}/approve")
    public ResponseEntity<ApiResponse> approveRecord(@PathVariable String recordId) {
        // Requires COLLEGE_ADMIN role
        collegeService.approveRecord(recordId);
        return ResponseEntity.ok(new ApiResponse(true, "Record approved successfully."));
    }

    @PostMapping("/verifications/{recordId}/reject")
    public ResponseEntity<ApiResponse> rejectRecord(@PathVariable String recordId) {
        // Requires COLLEGE_ADMIN role
        collegeService.rejectRecord(recordId);
        return ResponseEntity.ok(new ApiResponse(true, "Record rejected successfully."));
    }
}