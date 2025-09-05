package com.example.alumni.serviceimpl;

import com.example.alumni.dto.AuthResponse;
import com.example.alumni.dto.LoginRequest;
import com.example.alumni.dto.RegisterRequest;
import com.example.alumni.dto.VerifyOtpRequest;
import com.example.alumni.entity.AlumniProfile;
import com.example.alumni.entity.College;
import com.example.alumni.entity.User;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.ConflictException;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.AlumniProfileRepository;
import com.example.alumni.repository.CollegeRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.security.JwtUtil;
import com.example.alumni.service.AuthService;
import com.example.alumni.util.AadhaarOtpValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AlumniProfileRepository alumniProfileRepository;
    private final CollegeRepository collegeRepository; // Injected for registration
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AadhaarOtpValidator aadhaarOtpValidator;

    /**
     * Registers a new alumni user. This involves creating a User record and an associated
     * AlumniProfile linked to a specific College. The user is initially created in an
     * inactive state pending Aadhaar OTP verification.
     *
     * @param request The registration data from the DTO, including the collegeId.
     * @throws ConflictException if a user with the given email already exists.
     * @throws NotFoundException if the provided collegeId does not exist.
     */
    @Override
    @Transactional
    public void registerAlumni(RegisterRequest request) {
        // 1. Check if a user with the same email already exists.
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("User with email " + request.getEmail() + " already exists.");
        }

        // 2. Fetch the College entity from the database using the ID from the request.
        // This ensures the alumnus is associated with a valid, existing college.
        College college = collegeRepository.findById(request.getCollegeId())
                .orElseThrow(() -> new NotFoundException("College not found with ID: " + request.getCollegeId()));

        // 3. Create the new User entity.
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ALUMNI);
        user.setActive(false); // User remains inactive until OTP verification.

        // 4. Create the associated AlumniProfile.
        AlumniProfile alumniProfile = new AlumniProfile();
        alumniProfile.setFirstName(request.getFirstName());
        alumniProfile.setLastName(request.getLastName());
        alumniProfile.setCollege(college); // Set the fetched College entity.

        // 5. Link the User and AlumniProfile entities together bidirectionally.
        user.setAlumniProfile(alumniProfile);
        alumniProfile.setUser(user);

        // 6. Save the User entity. Thanks to CascadeType.ALL, the linked AlumniProfile will be saved automatically.
        userRepository.save(user);

        // 7. Trigger the Aadhaar OTP verification process.
        aadhaarOtpValidator.sendOtp(user, request.getAadhaarNumber());
    }

    /**
     * Authenticates a user with their email and password and returns a JWT.
     */
    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found after authentication"));

        String token = jwtUtil.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }

    /**
     * Verifies an Aadhaar OTP to activate a newly registered user account.
     */
    @Override
    @Transactional
    public void verifyAadhaarOtp(VerifyOtpRequest request) {
        User user = userRepository.findByEmail(request.getUserIdentifier())
                .orElseThrow(() -> new NotFoundException("User with identifier " + request.getUserIdentifier() + " not found."));

        if (user.isActive()) {
            throw new IllegalStateException("This account is already active.");
        }

        boolean isOtpValid = aadhaarOtpValidator.validate(user, request.getOtp());
        if (!isOtpValid) {
            throw new IllegalArgumentException("Invalid OTP provided. Please try again.");
        }

        user.setActive(true);
        userRepository.save(user);
    }
}