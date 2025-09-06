package com.example.alumni.serviceimpl;

import com.example.alumni.dto.WorkExperienceRequest;
import com.example.alumni.dto.WorkExperienceResponse;
import com.example.alumni.entity.AlumniProfile;
import com.example.alumni.entity.Company;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.exception.ResourceNotFoundException;
import com.example.alumni.repository.AlumniProfileRepository;
import com.example.alumni.repository.CompanyRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    // FIX 1: Using AlumniProfileRepository because your entity links to AlumniProfile
    private final AlumniProfileRepository alumniProfileRepository; 
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public WorkExperienceResponse createWorkExperience(WorkExperienceRequest request) {
        // FIX 2: Finding an AlumniProfile, not a User
        AlumniProfile alumni = alumniProfileRepository.findById(request.getAlumniUserId())
            .orElseThrow(() -> new ResourceNotFoundException("Alumni profile not found with id: " + request.getAlumniUserId()));

        Company company = companyRepository.findById(request.getCompanyId())
            .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + request.getCompanyId()));

        WorkExperience newExperience = new WorkExperience();
        
        // FIX 3: Using .setAlumniProfile(), which matches your entity
        newExperience.setAlumniProfile(alumni); 
        
        newExperience.setCompany(company);
        newExperience.setJobTitle(request.getJobTitle());
        newExperience.setStatus(RecordStatus.PENDING_AUTHORITY); // Using your correct enum

        WorkExperience savedExperience = workExperienceRepository.save(newExperience);
        return mapToResponse(savedExperience);
    }

    @Override
    @Transactional
    public WorkExperienceResponse verifyExperience(String experienceId) {
        WorkExperience experience = workExperienceRepository.findById(experienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Work experience record not found with id: " + experienceId));
        
        experience.setStatus(RecordStatus.VERIFIED);
        WorkExperience updatedExperience = workExperienceRepository.save(experience);
        return mapToResponse(updatedExperience);
    }

    private WorkExperienceResponse mapToResponse(WorkExperience experience) {
        // FIX 4: Using .getAlumniProfile() and then getting the name from it
        String alumniName = experience.getAlumniProfile().getFirstName() + " " + experience.getAlumniProfile().getLastName();
        
        return WorkExperienceResponse.builder()
                .experienceId(experience.getExperienceId())
                .alumniName(alumniName)
                .companyName(experience.getCompany().getName())
                .jobTitle(experience.getJobTitle())
                .status(experience.getStatus())
                .build();
    }
}