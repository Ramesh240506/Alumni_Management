package com.example.alumni.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alumni.dto.CourseRequest;
import com.example.alumni.dto.CourseResponse;
import com.example.alumni.service.ProfessionalCourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class ProfessionalCourseController {
    
    private final ProfessionalCourseService professionalCourseService;
    
    @PostMapping("/request-verification")
    public ResponseEntity<CourseResponse> requestVerification(@Valid @RequestBody CourseRequest request) {
        // Requires ALUMNI role
        CourseResponse response = professionalCourseService.requestCourseVerification(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}