package com.example.alumni.serviceimpl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;
import com.example.alumni.entity.CourseProvider;
import com.example.alumni.entity.ProfessionalCourse;
import com.example.alumni.entity.User;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.CourseProviderRepository;
import com.example.alumni.repository.ProfessionalCourseRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.ProfessionalCourseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessionalCourseServiceImpl implements ProfessionalCourseService {

    private final ProfessionalCourseRepository professionalCourseRepository;
    private final CourseProviderRepository courseProviderRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CourseResponse requestCourseVerification(CourseRequest request) {
        User currentUser = getCurrentUser();
        if (currentUser.getRole() != UserRole.ALUMNI) {
            throw new UnauthorizedException("Only alumni can request course verifications.");
        }
        CourseProvider provider = courseProviderRepository.findById(request.getProviderId())
                .orElseThrow(() -> new NotFoundException("Course Provider not found with ID: " + request.getProviderId()));
        ProfessionalCourse newCourse = new ProfessionalCourse();
        newCourse.setCourseName(request.getCourseName());
        newCourse.setCompletionDate(request.getCompletionDate());
        newCourse.setAlumniProfile(currentUser.getAlumniProfile());
        newCourse.setCourseProvider(provider);
        newCourse.setStatus(RecordStatus.PENDING_AUTHORITY);
        ProfessionalCourse savedCourse = professionalCourseRepository.save(newCourse);
        return mapToCourseResponse(savedCourse);
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