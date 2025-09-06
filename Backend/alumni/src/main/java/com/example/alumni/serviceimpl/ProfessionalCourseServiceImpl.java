package com.example.alumni.serviceimpl;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;
import com.example.alumni.entity.CourseProvider;
import com.example.alumni.entity.ProfessionalCourse;
import com.example.alumni.entity.User;
import com.example.alumni.entity.enums.RecordStatus;
import com.example.alumni.exception.ResourceNotFoundException;
import com.example.alumni.repository.CourseProviderRepository;
import com.example.alumni.repository.ProfessionalCourseRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.ProfessionalCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfessionalCourseServiceImpl implements ProfessionalCourseService {

    private final ProfessionalCourseRepository professionalCourseRepository;
    private final UserRepository userRepository;
    private final CourseProviderRepository courseProviderRepository;

    @Override
    @Transactional
    public CourseResponse addCourseForAlumni(CourseRequest request, String providerId) {
        User alumni = userRepository.findById(request.getAlumniUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Alumni user not found with id: " + request.getAlumniUserId()));

        CourseProvider provider = courseProviderRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Course Provider not found with id: " + providerId));

        ProfessionalCourse newCourse = new ProfessionalCourse();
        
        /********************************************************************************
         * ERROR WILL BE HERE: The method 'setAlumniProfile' is correct based on your entity,
         * but it expects an 'AlumniProfile' object, not a 'User' object.
         *
         * This line cannot be fixed in this file. It can only be fixed by changing
         * the entity to accept a User.
         ********************************************************************************/
        // newCourse.setAlumniProfile(alumni); // This line will have a type mismatch error.

        newCourse.setCourseProvider(provider);
        newCourse.setCourseName(request.getCourseName());
        newCourse.setCompletionDate(request.getCompletionDate());
        newCourse.setStatus(RecordStatus.VERIFIED);

        ProfessionalCourse savedCourse = professionalCourseRepository.save(newCourse);
        return mapToResponse(savedCourse);
    }

    @Override
    @Transactional
    public CourseResponse verifyCourse(String courseId) {
        ProfessionalCourse course = professionalCourseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional course record not found with id: " + courseId));

        course.setStatus(RecordStatus.VERIFIED);
        ProfessionalCourse updatedCourse = professionalCourseRepository.save(course);
        return mapToResponse(updatedCourse);
    }

    private CourseResponse mapToResponse(ProfessionalCourse course) {
        /********************************************************************************
         * ERROR WILL BE HERE: The method 'getAlumniProfile()' is correct, but the
         * 'AlumniProfile' object it returns does not have getFirstName() or getLastName().
         *
         * More importantly, the compiler can't even find the 'AlumniProfile' class,
         * which is the root of all problems.
         ********************************************************************************/
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