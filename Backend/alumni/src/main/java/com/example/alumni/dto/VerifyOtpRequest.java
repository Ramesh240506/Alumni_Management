package com.example.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpRequest {
    @NotBlank(message = "User identifier is required (e.g., email or user ID)")
    private String userIdentifier;

    @NotBlank(message = "OTP is required")
    private String otp;
}