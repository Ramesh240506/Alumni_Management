package com.example.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CourseRequest {
    @NotBlank
    private String courseName;
    @NotNull
    private LocalDate completionDate;
    @NotBlank
    private String providerId; // The alumnus selects the provider
}