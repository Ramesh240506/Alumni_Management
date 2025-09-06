package com.example.alumni.serviceimpl;

import com.example.alumni.dto.AlumniProfileRequest;
import com.example.alumni.dto.AlumniProfileResponse;
import com.example.alumni.dto.DigitalIdResponse;
import com.example.alumni.dto.DonationResponse;
import com.example.alumni.entity.*;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.*;
import com.example.alumni.service.AlumniService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumniServiceImpl implements AlumniService {

    private final UserRepository userRepository;
    private final AlumniProfileRepository alumniProfileRepository;
    private final DigitalIdRepository digitalIdRepository;
    private final DonationRepository donationRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AlumniProfileResponse getCurrentAlumniProfile() {
        User currentUser = getCurrentUser();
        AlumniProfile profile = alumniProfileRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new NotFoundException("Alumni profile not found for the current user."));
        return mapToAlumniProfileResponse(profile);
    }

    @Override
    @Transactional
    public AlumniProfileResponse updateCurrentAlumniProfile(AlumniProfileRequest request) {
        User currentUser = getCurrentUser();
        AlumniProfile profile = alumniProfileRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new NotFoundException("Alumni profile not found for the current user."));

        if (request.getPhone() != null) {
            profile.setPhone(request.getPhone());
        }
        if (request.getPrivacySettings() != null) {
            try {
                profile.setPrivacySettings(objectMapper.writeValueAsString(request.getPrivacySettings()));
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Invalid privacy settings format.");
            }
        }
        AlumniProfile updatedProfile = alumniProfileRepository.save(profile);
        return mapToAlumniProfileResponse(updatedProfile);
    }

    @Override
    public DigitalIdResponse getDigitalIdForCurrentUser() {
        User currentUser = getCurrentUser();
        DigitalId digitalId = digitalIdRepository.findByAlumniProfile_AlumniUserId(currentUser.getUserId())
                .orElseThrow(() -> new NotFoundException("Digital ID not found for the current user."));

        return DigitalIdResponse.builder()
                .idToken(digitalId.getIdToken())
                .isValid(digitalId.isValid())
                .createdAt(digitalId.getCreatedAt())
                .expiresAt(digitalId.getExpiresAt())
                .build();
    }

    @Override
    public List<DonationResponse> getDonationHistoryForCurrentUser() {
        User currentUser = getCurrentUser();
        List<Donation> donations = donationRepository.findByDonor_UserIdOrderByDonatedAtDesc(currentUser.getUserId());
        return donations.stream()
                .map(this::mapToDonationResponse)
                .collect(Collectors.toList());
    }

    // --- THIS IS THE HACKATHON-MODE HELPER METHOD ---
    /**
     * Retrieves the currently authenticated User from the database.
     * HACKATHON MODE: Since security is disabled, this method is hardcoded to return
     * a specific test user ('rahul.sharma@example.com') to allow for testing of protected endpoints.
     * @return The hardcoded test User entity.
     */
    private User getCurrentUser() {
        System.out.println("\n--- [HACKATHON MODE] getCurrentUser() is returning a hardcoded test user. ---\n");

        // This email MUST exist in your database from the dummy data script.
        String testUserEmail = "rahul.sharma@example.com";

        return userRepository.findByEmail(testUserEmail)
                .orElseThrow(() -> new NotFoundException("HACKATHON TEST USER NOT FOUND: The user '" + testUserEmail + "' does not exist in the database. Please run the dummy data script."));
    }
    // --- END OF HACKATHON-MODE HELPER METHOD ---

    private AlumniProfileResponse mapToAlumniProfileResponse(AlumniProfile profile) {
        // Lazy loading requires the college to be fetched within a transaction or with an EAGER fetch.
        // For simplicity, we assume it's available.
        AlumniProfileResponse.CollegeDto collegeDto = AlumniProfileResponse.CollegeDto.builder()
                .collegeId(profile.getCollege().getCollegeId())
                .name(profile.getCollege().getName())
                .build();

        return AlumniProfileResponse.builder()
                .alumniUserId(profile.getAlumniUserId())
                .email(profile.getUser().getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phone(profile.getPhone())
                .privacySettings(profile.getPrivacySettings())
                .college(collegeDto)
                .build();
    }

    private DonationResponse mapToDonationResponse(Donation donation) {
        DonationResponse.CampaignInfo campaignInfo = DonationResponse.CampaignInfo.builder()
                .campaignId(donation.getCampaign().getCampaignId())
                .title(donation.getCampaign().getTitle())
                .build();

        return DonationResponse.builder()
                .donationId(donation.getDonationId())
                .amount(donation.getAmount())
                .isAnonymous(donation.isAnonymous())
                .donatedAt(donation.getDonatedAt())
                .campaign(campaignInfo)
                .build();
    }
}