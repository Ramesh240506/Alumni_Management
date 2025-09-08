package com.example.alumni.serviceimpl;

import com.example.alumni.dto.WorkExperienceResponse;
import com.example.alumni.entity.User;
import com.example.alumni.entity.UserAffiliation;
import com.example.alumni.entity.WorkExperience;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.repository.WorkExperienceRepository;
import com.example.alumni.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {

    private final WorkExperienceRepository workExperienceRepository;
    private final UserRepository userRepository;

    @Override
    public List<WorkExperienceResponse> getPendingVerifications() {
        User currentUser = getCurrentUser();
        String companyId = getCompanyIdForCurrentUser(currentUser);

        // This query needs to be added to WorkExperienceRepository
        return workExperienceRepository.findByCompany_CompanyIdAndStatus(companyId, RecordStatus.PENDING_AUTHORITY)
                .stream()
                .map(this::mapToWorkExperienceResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveWorkExperience(String experienceId) {
        WorkExperience record = findAndAuthorize(experienceId);
        record.setStatus(RecordStatus.PENDING_COLLEGE);
        workExperienceRepository.save(record);
    }

    @Override
    @Transactional
    public void rejectWorkExperience(String experienceId) {
        WorkExperience record = findAndAuthorize(experienceId);
        record.setStatus(RecordStatus.REJECTED);
        workExperienceRepository.save(record);
    }

    private WorkExperience findAndAuthorize(String experienceId) {
        User currentUser = getCurrentUser();
        String companyId = getCompanyIdForCurrentUser(currentUser);

        WorkExperience record = workExperienceRepository.findById(experienceId)
            .orElseThrow(() -> new NotFoundException("Work experience record not found."));

        if (!record.getCompany().getCompanyId().equals(companyId)) {
            throw new UnauthorizedException("You are not authorized to modify this record.");
        }
        return record;
    }

    private String getCompanyIdForCurrentUser(User user) {
        if (user.getRole() != UserRole.EMPLOYER_REP) {
            throw new UnauthorizedException("Only Employer Representatives can perform this action.");
        }
        return user.getAffiliations().stream().findFirst()
                .map(UserAffiliation::getEntityId)
                .orElseThrow(() -> new IllegalStateException("Employer Representative is not affiliated with any company."));
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found."));
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