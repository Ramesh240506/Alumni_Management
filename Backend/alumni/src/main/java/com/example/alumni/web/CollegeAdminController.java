package com.example.alumni.web;

import com.example.alumni.dto.ApiResponse;
import com.example.alumni.dto.PendingAlumniDto;
import com.example.alumni.dto.PendingRecordDto;
import com.example.alumni.service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
public class CollegeAdminController {

    private final CollegeService collegeService;

    @GetMapping("/pending-registrations")
    public ResponseEntity<List<PendingAlumniDto>> getPendingAlumniApprovals() {
        return ResponseEntity.ok(collegeService.getPendingAlumniRegistrations());
    }

    @PostMapping("/approve-alumni/{id}")
    public ResponseEntity<ApiResponse> approveAlumniRegistration(@PathVariable String id) {
        collegeService.approveAlumni(id);
        return ResponseEntity.ok(new ApiResponse("Alumni registration approved successfully."));
    }

    @GetMapping("/pending-records")
    public ResponseEntity<List<PendingRecordDto>> getPendingRecords() {
        return ResponseEntity.ok(collegeService.getPendingRecords());
    }

    @PostMapping("/approve-record/{recordId}")
    public ResponseEntity<ApiResponse> approveRecord(@PathVariable String recordId, @RequestParam(defaultValue = "true") boolean approve) {
        collegeService.approveRecord(recordId, approve);
        String message = approve ? "Record approved successfully." : "Record rejected successfully.";
        return ResponseEntity.ok(new ApiResponse(message));
    }
}