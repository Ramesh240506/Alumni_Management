package com.example.alumni.serviceimpl;

import com.example.alumni.dto.JobRequest;
import com.example.alumni.dto.JobResponse;
import com.example.alumni.entity.*;
import com.example.alumni.entity.enums.JobStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.CompanyRepository;
import com.example.alumni.repository.JobPostingRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository; // Needed to link company to job

    @Override
    public List<JobResponse> getOpenJobs() {
        return jobPostingRepository.findByStatusOrderByCreatedAtDesc(JobStatus.OPEN)
                .stream()
                .map(this::mapToJobResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobResponse getJobById(String jobId) {
        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job posting not found with ID: " + jobId));
        return mapToJobResponse(jobPosting);
    }

    @Override
    @Transactional
    public JobResponse createJob(JobRequest jobRequest) {
        User currentUser = getCurrentUser();
        Company company;
        boolean isReferral = false;

        if (currentUser.getRole() == UserRole.EMPLOYER_REP) {
            // If user is an employer, find their affiliated company
            UserAffiliation affiliation = currentUser.getAffiliations().stream().findFirst()
                    .orElseThrow(() -> new IllegalStateException("Employer rep is not affiliated with any company."));
            company = companyRepository.findById(affiliation.getEntityId())
                    .orElseThrow(() -> new NotFoundException("Affiliated company not found."));
        } else if (currentUser.getRole() == UserRole.ALUMNI) {
            // If user is an alumnus, this is a referral. Find their latest employer.
            isReferral = true;
            WorkExperience latestWork = currentUser.getAlumniProfile().getWorkExperiences().stream()
                    .findFirst() // Assuming the first is the latest, you could add date logic here
                    .orElseThrow(() -> new IllegalStateException("Cannot make a referral without a verified work experience."));
            company = latestWork.getCompany();
        } else {
            throw new IllegalStateException("Only Employer Representatives or Alumni can post jobs.");
        }

        JobPosting newJob = new JobPosting();
        newJob.setTitle(jobRequest.getTitle());
        newJob.setDescription(jobRequest.getDescription());
        newJob.setLocation(jobRequest.getLocation());
        newJob.setType(jobRequest.getType());
        newJob.setReferral(isReferral);
        newJob.setPostedByUser(currentUser);
        newJob.setCompany(company);

        JobPosting savedJob = jobPostingRepository.save(newJob);
        return mapToJobResponse(savedJob);
    }
    
    // Helper method to get the authenticated user
    private User getCurrentUser() {
        // ... (this method is the same as in AlumniServiceImpl)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
             throw new IllegalStateException("User is not authenticated.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }
    
    // Helper method for DTO mapping
    private JobResponse mapToJobResponse(JobPosting job) {
        JobResponse.CompanyInfo companyInfo = JobResponse.CompanyInfo.builder()
                .companyId(job.getCompany().getCompanyId())
                .name(job.getCompany().getName())
                .build();

        String posterName = "N/A";
        if (job.getPostedByUser().getAlumniProfile() != null) {
            posterName = job.getPostedByUser().getAlumniProfile().getFirstName() + " " + job.getPostedByUser().getAlumniProfile().getLastName();
        }

        JobResponse.UserInfo userInfo = JobResponse.UserInfo.builder()
                .userId(job.getPostedByUser().getUserId())
                .name(posterName)
                .build();
                
        return JobResponse.builder()
                .jobId(job.getJobId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .type(job.getType())
                .isReferral(job.isReferral())
                .createdAt(job.getCreatedAt())
                .company(companyInfo)
                .postedBy(userInfo)
                .build();
    }
}