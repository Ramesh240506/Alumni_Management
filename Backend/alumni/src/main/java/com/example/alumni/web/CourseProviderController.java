package com.example.alumni.web;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;
import com.example.alumni.service.ProfessionalCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/provider")
@RequiredArgsConstructor
public class CourseProviderController {

    private final ProfessionalCourseService professionalCourseService;

  
    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> addCompletedCourseForAlumni(@RequestBody CourseRequest courseRequest) {
        String providerId = "replace-with-authenticated-provider-id"; 

        CourseResponse response = professionalCourseService.addCourseForAlumni(courseRequest, providerId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

   
    @PostMapping("/verify-course/{courseId}")
    public ResponseEntity<CourseResponse> submitCourseVerification(@PathVariable String courseId) {
        CourseResponse response = professionalCourseService.verifyCourse(courseId);
        return ResponseEntity.ok(response);
    }
}