package com.example.alumni.serviceimpl;

import com.example.alumni.entity.User;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.alumni.dto.ConnectionResponseRequest;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final WorkExperienceRepository workExperienceRepository;
    private final UserRepository userRepository;

    // This is where an employer would approve a request, moving it to PENDING_COLLEGE.
    @Transactional
    public void approveWorkExperience(String experienceId) {
        WorkExperience record = workExperienceRepository.findById(experienceId)
            .orElseThrow(() -> new NotFoundException("Work experience record not found."));
        
        // Add authorization logic here to ensure the current user works for this company
        
        record.setStatus(RecordStatus.PENDING_COLLEGE);
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