package com.example.alumni.web;

import com.example.alumni.dto.*;
import com.example.alumni.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling authentication-related requests such as
 * user registration, login, and OTP verification.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder; // Injected for the hashing utility

    /**
     * Endpoint for alumni registration.
     * It accepts user details, validates them, and passes them to the AuthService
     * to begin the registration and Aadhaar OTP verification process.
     *
     * @param registerRequest DTO containing registration information.
     * @return A success message indicating that registration has started.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerAlumni(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerAlumni(registerRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Registration successful. Please check your device for an Aadhaar OTP to verify your account."));
    }

    /**
     * Endpoint for user login.
     * Authenticates the user based on email and password and returns a JWT upon success.
     *
     * @param loginRequest DTO containing login credentials.
     * @return An AuthResponse containing the JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    /**
     * Endpoint for verifying the Aadhaar OTP to complete the registration process.
     *
     * @param verifyOtpRequest DTO containing the user identifier and the OTP.
     * @return A success message upon successful verification.
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest verifyOtpRequest) {
        authService.verifyAadhaarOtp(verifyOtpRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Aadhaar verification successful. Your account is now active."));
    }


    // --- TEMPORARY UTILITY ENDPOINT ---
    /**
     * A utility endpoint to generate a BCrypt hash for a given password.
     * USE THIS TO CREATE THE CORRECT HASH FOR YOUR DUMMY DATA SCRIPT.
     * How to use:
     * 1. Run the application.
     * 2. Go to http://localhost:8080/alumni-api/auth/hash/password123 in your browser.
     * 3. Copy the returned hash string.
     * 4. Paste it into your SQL UPDATE script.
     * 5. (Optional but recommended) Delete this method after you are done.
     *
     * @param password The plain text password to hash.
     * @return The BCrypt-hashed password.
     */
    @GetMapping("/hash/{password}")
    public ResponseEntity<String> getHash(@PathVariable String password) {
        return ResponseEntity.ok(passwordEncoder.encode(password));
    }
}