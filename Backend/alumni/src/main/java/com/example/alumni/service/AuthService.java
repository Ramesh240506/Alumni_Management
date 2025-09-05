package com.example.alumni.service;

import com.example.alumni.dto.AuthResponse;
import com.example.alumni.dto.LoginRequest;
import com.example.alumni.dto.RegisterRequest;
import com.example.alumni.dto.VerifyOtpRequest;

public interface AuthService {

    /**
     * Registers a new alumni user, creates their profile in an unverified state,
     * and triggers the Aadhaar OTP verification process.
     * @param request The registration data.
     */
    void registerAlumni(RegisterRequest request);

    /**
     * Authenticates a user and returns a JWT if credentials are valid.
     * @param request The login credentials.
     * @return An AuthResponse containing the JWT.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Verifies the Aadhaar OTP provided by the user to activate their account.
     * @param request The OTP data.
     */
    void verifyAadhaarOtp(VerifyOtpRequest request);
}