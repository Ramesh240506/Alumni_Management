package com.example.alumni.serviceimpl;

import com.example.alumni.dto.AlumniNetworkResponse;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    // --- THIS IS THE FINAL, SECURE HELPER METHOD ---
    /**
     * Retrieves the currently authenticated User from the database by reading the
     * security context established by the JwtFilter.
     * @return The authenticated User entity.
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("User is not authenticated. Cannot perform this action.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + username));
    }
    // --- END OF SECURE HELPER METHOD ---

    private AlumniProfileResponse mapToAlumniProfileResponse(AlumniProfile profile) {
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

    // --- ADD THIS NEW METHOD ---
public List<AlumniNetworkResponse> getAlumniNetwork() {
    User currentUser = getCurrentUser();
    String collegeId = currentUser.getAlumniProfile().getCollege().getCollegeId();

    List<AlumniProfile> network = alumniProfileRepository.findByCollege_CollegeIdAndAlumniUserIdNot(collegeId, currentUser.getUserId());

    return network.stream()
            .map(this::mapToAlumniNetworkResponse)
            .collect(Collectors.toList());
}

// --- ADD THIS NEW HELPER METHOD ---
private AlumniNetworkResponse mapToAlumniNetworkResponse(AlumniProfile profile) {
    // Mocked lat/lng for demonstration. In a real app, this would come from the database.
    double lat = (Math.random() * (40.0 - 10.0)) + 10.0; // Random lat for variety
    double lng = (Math.random() * (100.0 - 70.0)) + 70.0; // Random lng for variety

    return AlumniNetworkResponse.builder()
            .alumniUserId(profile.getAlumniUserId())
            .firstName(profile.getFirstName())
            .lastName(profile.getLastName())
            .currentCity(profile.getCurrentCity()) // Assumes currentCity field exists
            .lat(lat)
            .lng(lng)
            .build();
}
}