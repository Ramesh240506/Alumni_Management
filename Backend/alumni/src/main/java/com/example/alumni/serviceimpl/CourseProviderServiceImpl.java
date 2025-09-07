package com.example.alumni.serviceimpl;

import com.example.alumni.dto.CourseResponse;
import com.example.alumni.entity.ProfessionalCourse;
import com.example.alumni.entity.User;
import com.example.alumni.entity.UserAffiliation;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.ProfessionalCourseRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.CourseProviderService;
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
public class CourseProviderServiceImpl implements CourseProviderService {

    private final ProfessionalCourseRepository professionalCourseRepository;
    private final UserRepository userRepository;

    @Override
    public List<CourseResponse> getPendingVerifications() {
        User currentUser = getCurrentUser();
        if (currentUser.getRole() != UserRole.PROVIDER_REP) {
            throw new UnauthorizedException("Only Course Provider Representatives can view pending verifications.");
        }

        String providerId = getProviderIdForCurrentUser(currentUser);

        return professionalCourseRepository.findByCourseProvider_ProviderIdAndStatus(providerId, RecordStatus.PENDING_AUTHORITY)
                .stream()
                .map(this::mapToCourseResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveVerification(String courseRecordId) {
        User currentUser = getCurrentUser();
        String providerId = getProviderIdForCurrentUser(currentUser);

        ProfessionalCourse course = professionalCourseRepository.findById(courseRecordId)
                .orElseThrow(() -> new NotFoundException("Course record not found with ID: " + courseRecordId));

        // Authorization check: Ensure the record belongs to this provider.
        if (!course.getCourseProvider().getProviderId().equals(providerId)) {
            throw new UnauthorizedException("You are not authorized to approve this record.");
        }
        
        course.setStatus(RecordStatus.PENDING_COLLEGE);
        professionalCourseRepository.save(course);
    }

    @Override
    @Transactional
    public void rejectVerification(String courseRecordId) {
        User currentUser = getCurrentUser();
        String providerId = getProviderIdForCurrentUser(currentUser);

        ProfessionalCourse course = professionalCourseRepository.findById(courseRecordId)
                .orElseThrow(() -> new NotFoundException("Course record not found with ID: " + courseRecordId));

        if (!course.getCourseProvider().getProviderId().equals(providerId)) {
            throw new UnauthorizedException("You are not authorized to reject this record.");
        }

        course.setStatus(RecordStatus.REJECTED);
        professionalCourseRepository.save(course);
    }

    private String getProviderIdForCurrentUser(User user) {
        return user.getAffiliations().stream().findFirst()
                .map(UserAffiliation::getEntityId)
                .orElseThrow(() -> new IllegalStateException("Course Provider Representative is not affiliated with any provider."));
    }
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }

    private CourseResponse mapToCourseResponse(ProfessionalCourse course) {
        CourseResponse.ProviderInfo providerInfo = CourseResponse.ProviderInfo.builder()
                .providerId(course.getCourseProvider().getProviderId())
                .name(course.getCourseProvider().getName())
                .build();
        return CourseResponse.builder()
                .courseRecordId(course.getCourseRecordId())
                .courseName(course.getCourseName())
                .completionDate(course.getCompletionDate())
                .status(course.getStatus())
                .provider(providerInfo)
                .build();
    }
}