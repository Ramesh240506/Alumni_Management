package com.example.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkExperienceRequest {
    @NotBlank
    private String jobTitle;
    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;
}