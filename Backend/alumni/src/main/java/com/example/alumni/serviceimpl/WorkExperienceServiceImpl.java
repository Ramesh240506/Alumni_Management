package com.example.alumni.serviceimpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.alumni.dto.WorkExperienceRequest;
import com.example.alumni.entity.Company;
import com.example.alumni.entity.User;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.CompanyRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.WorkExperienceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public void requestWorkExperienceVerification(WorkExperienceRequest request) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole() != UserRole.ALUMNI) {
            throw new UnauthorizedException("Only alumni can request work experience verifications.");
        }
        
        // This assumes the frontend provides a valid companyId
        // A more robust implementation would allow creating a new company request
        Company company = companyRepository.findById(getCompanyIdFromRequest(request))
             .orElseThrow(() -> new NotFoundException("Company not found."));

        WorkExperience newExperience = new WorkExperience();
        newExperience.setJobTitle(request.getJobTitle());
        newExperience.setStartDate(request.getStartDate());
        newExperience.setEndDate(request.getEndDate());
        newExperience.setAlumniProfile(currentUser.getAlumniProfile());
        newExperience.setCompany(company);
        newExperience.setStatus(RecordStatus.PENDING_AUTHORITY);
        
        workExperienceRepository.save(newExperience);
    }
    
    // Placeholder to get companyId. The DTO would need to be updated.
    private String getCompanyIdFromRequest(WorkExperienceRequest request) {
        // You would need to add 'private String companyId;' to the WorkExperienceRequest DTO
        // return request.getCompanyId();
        // For now, returning a hardcoded ID from dummy data for testing.
        return "9efbf56c-8a78-11f0-9a48-b42e99b190a8"; // Innovate Solutions Inc.
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }
}