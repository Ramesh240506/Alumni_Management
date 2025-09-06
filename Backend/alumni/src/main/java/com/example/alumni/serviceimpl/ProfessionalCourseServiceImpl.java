package com.example.alumni.serviceimpl;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;
import com.example.alumni.entity.AlumniProfile;
import com.example.alumni.entity.CourseProvider;
import com.example.alumni.entity.ProfessionalCourse;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.exception.ResourceNotFoundException;
import com.example.alumni.repository.AlumniProfileRepository;
import com.example.alumni.repository.CourseProviderRepository;
import com.example.alumni.repository.ProfessionalCourseRepository;
import com.example.alumni.service.ProfessionalCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// No more import for UUID

/**
 * Implementation of the ProfessionalCourseService interface.
 */
@Service
@RequiredArgsConstructor
public class ProfessionalCourseServiceImpl implements ProfessionalCourseService {

    private final ProfessionalCourseRepository professionalCourseRepository;
    // FIX: Using AlumniProfileRepository as per your entity definition
    private final AlumniProfileRepository alumniProfileRepository; 
    private final CourseProviderRepository courseProviderRepository;

    @Override
    @Transactional
    // FIX: Added providerId parameter to match interface
    public CourseResponse addCourseForAlumni(CourseRequest request, String providerId) { 
        AlumniProfile alumni = alumniProfileRepository.findById(request.getAlumniUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Alumni profile not found with id: " + request.getAlumniUserId()));

        CourseProvider provider = courseProviderRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Course Provider not found with id: " + providerId));

        // FIX: Replaced .builder() with standard setters to match your entity
        ProfessionalCourse newCourse = new ProfessionalCourse();
        newCourse.setAlumniProfile(alumni);
        newCourse.setCourseProvider(provider);
        newCourse.setCourseName(request.getCourseName());
        // Assuming your CourseRequest DTO will have this field
        // newCourse.setCompletionDate(request.getCompletionDate()); 
        newCourse.setStatus(RecordStatus.VERIFIED); // Added by provider, so it's considered verified.

        ProfessionalCourse savedCourse = professionalCourseRepository.save(newCourse);
        return mapToResponse(savedCourse);
    }

    @Override
    @Transactional
    // FIX: Changed parameter from UUID to String
    public CourseResponse verifyCourse(String courseId) { 
        ProfessionalCourse course = professionalCourseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional course record not found with id: " + courseId));

        // Add security checks here to ensure the current user is the correct provider.

        course.setStatus(RecordStatus.VERIFIED);
        ProfessionalCourse updatedCourse = professionalCourseRepository.save(course);
        return mapToResponse(updatedCourse);
    }

    /**
     * Helper method to map a ProfessionalCourse entity to a CourseResponse DTO.
     */
    private CourseResponse mapToResponse(ProfessionalCourse course) {
        // FIX: Mapping now uses AlumniProfile, which is correct for your entity
        String alumniName = course.getAlumniProfile().getFirstName() + " " + course.getAlumniProfile().getLastName();
        
        return CourseResponse.builder()
                .courseRecordId(course.getCourseRecordId())
                .courseName(course.getCourseName())
                .alumniName(alumniName)
                .providerName(course.getCourseProvider().getName())
                .status(course.getStatus())
                .build();
    }
}