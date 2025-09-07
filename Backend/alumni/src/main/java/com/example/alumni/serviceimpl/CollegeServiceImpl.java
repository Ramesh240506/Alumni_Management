package com.example.alumni.serviceimpl;

import com.example.alumni.dto.AcademicRecordDto;
import com.example.alumni.entity.User;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.CollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {

    private final WorkExperienceRepository workExperienceRepository; // Example for one record type
    private final UserRepository userRepository;
    
    @Override
    public List<AcademicRecordDto> getPendingVerifications() {
        // This logic would need to be expanded to query all record types (WorkExperience, ProfessionalCourse)
        // and find records with status PENDING_COLLEGE for the admin's college.
        // For this example, we'll return an empty list.
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public void approveRecord(String recordId) {
        // Generic record approval logic would go here.
        // For example, finding a WorkExperience record:
        WorkExperience record = workExperienceRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("Record not found with ID: " + recordId));
        record.setStatus(RecordStatus.VERIFIED);
        workExperienceRepository.save(record);
    }

    @Override
    @Transactional
    public void rejectRecord(String recordId) {
        WorkExperience record = workExperienceRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("Record not found with ID: " + recordId));
        record.setStatus(RecordStatus.REJECTED);
        workExperienceRepository.save(record);
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
             throw new IllegalStateException("User is not authenticated.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }
}