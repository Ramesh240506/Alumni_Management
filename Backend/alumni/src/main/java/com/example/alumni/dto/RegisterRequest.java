package com.example.alumni.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Aadhaar number is required")
    @Size(min = 12, max = 12, message = "Aadhaar number must be 12 digits")
    private String aadhaarNumber;
    
    // --- THIS IS THE NEW FIELD ---
    @NotBlank(message = "College ID is required for registration")
    private String collegeId;
}