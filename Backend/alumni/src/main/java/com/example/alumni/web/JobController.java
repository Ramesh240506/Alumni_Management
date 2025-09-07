package com.example.alumni.web;

import com.example.alumni.dto.JobRequest;
import com.example.alumni.dto.JobResponse;
import com.example.alumni.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllOpenJobs() {
        return ResponseEntity.ok(jobService.getOpenJobs());
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable String jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }

    @PostMapping
    public ResponseEntity<JobResponse> createJobPosting(@Valid @RequestBody JobRequest jobRequest) {
        // NOTE: This endpoint requires authentication.
        // Your JwtFilter will handle this.
        JobResponse createdJob = jobService.createJob(jobRequest);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }
}