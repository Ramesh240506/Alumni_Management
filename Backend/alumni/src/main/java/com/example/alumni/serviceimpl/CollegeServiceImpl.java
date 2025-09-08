package com.example.alumni.serviceimpl;

import com.example.alumni.dto.WorkExperienceResponse; // Import the correct DTO
import com.example.alumni.entity.User;
import com.example.alumni.entity.UserAffiliation;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.CollegeService;
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
public class CollegeServiceImpl implements CollegeService {

    private final WorkExperienceRepository workExperienceRepository;
    private final UserRepository userRepository;

    @Override
    public List<WorkExperienceResponse> getPendingVerifications() {
        User currentUser = getCurrentUser();
        String collegeId = getCollegeIdForCurrentUser(currentUser);

        // Fetch all work experience records for this college that are awaiting final approval.
        List<WorkExperience> workVerifications = workExperienceRepository
            .findByAlumniProfile_College_CollegeIdAndStatus(collegeId, RecordStatus.PENDING_COLLEGE);

        // In a real app, you would also fetch ProfessionalCourse verifications and merge the lists.

        return workVerifications.stream()
                .map(this::mapToWorkExperienceResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveRecord(String recordId) {
        WorkExperience record = findAndAuthorize(recordId);
        record.setStatus(RecordStatus.VERIFIED);
        workExperienceRepository.save(record);
    }

    @Override
    @Transactional
    public void rejectRecord(String recordId) {
        WorkExperience record = findAndAuthorize(recordId);
        record.setStatus(RecordStatus.REJECTED);
        workExperienceRepository.save(record);
    }

    // --- HELPER METHODS - NOW FULLY IMPLEMENTED ---

    private WorkExperience findAndAuthorize(String recordId) {
        User currentUser = getCurrentUser();
        String collegeId = getCollegeIdForCurrentUser(currentUser);
        
        WorkExperience record = workExperienceRepository.findById(recordId)
            .orElseThrow(() -> new NotFoundException("Work experience record not found with ID: " + recordId));

        // Security Check: Ensure the record's alumnus is from the admin's college.
        if (!record.getAlumniProfile().getCollege().getCollegeId().equals(collegeId)) {
            throw new UnauthorizedException("You are not authorized to manage records for this college.");
        }
        return record;
    }

    private String getCollegeIdForCurrentUser(User user) {
        if (user.getRole() != UserRole.COLLEGE_ADMIN) {
            throw new UnauthorizedException("Only College Administrators can perform this action.");
        }
        return user.getAffiliations().stream().findFirst()
                .map(UserAffiliation::getEntityId)
                .orElseThrow(() -> new IllegalStateException("College Administrator is not affiliated with any college."));
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
             throw new IllegalStateException("User is not authenticated.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }

    private WorkExperienceResponse mapToWorkExperienceResponse(WorkExperience exp) {
        WorkExperienceResponse.AlumnusInfo alumnusInfo = WorkExperienceResponse.AlumnusInfo.builder()
            .userId(exp.getAlumniProfile().getAlumniUserId())
            .name(exp.getAlumniProfile().getFirstName() + " " + exp.getAlumniProfile().getLastName())
            .build();

        return WorkExperienceResponse.builder()
            .experienceId(exp.getExperienceId())
            .jobTitle(exp.getJobTitle())
            .startDate(exp.getStartDate())
            .endDate(exp.getEndDate())
            .status(exp.getStatus())
            .alumnus(alumnusInfo)
            .build();
    }
}