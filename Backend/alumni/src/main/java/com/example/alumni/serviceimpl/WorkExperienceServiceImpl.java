package com.example.alumni.serviceimpl;

import com.example.alumni.dto.WorkExperienceRequest;
import com.example.alumni.entity.User;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final UserRepository userRepository;
    
    @Override
    @Transactional
    public void requestWorkExperienceVerification(WorkExperienceRequest request) {
        // This method would be called by an ALUMNUS.
        // It would create a new WorkExperience record with status PENDING_AUTHORITY.
        // The logic would be similar to other creation services.
    }
    
    // Helper method to get the authenticated user
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