package com.example.alumni.service;

import com.example.alumni.dto.JobRequest;
import com.example.alumni.dto.JobResponse;

import java.util.List;

public interface JobService {

    /**
     * Retrieves all currently open job postings.
     * @return A list of JobResponse DTOs.
     */
    List<JobResponse> getOpenJobs();

    /**
     * Retrieves a single job posting by its ID.
     * @param jobId The ID of the job posting.
     * @return A JobResponse DTO.
     */
    JobResponse getJobById(String jobId);

    /**
     * Allows a logged-in user (Employer Rep or Alumnus) to create a new job posting.
     * @param jobRequest DTO containing the details of the job.
     * @return The created JobResponse DTO.
     */
    JobResponse createJob(JobRequest jobRequest);
}