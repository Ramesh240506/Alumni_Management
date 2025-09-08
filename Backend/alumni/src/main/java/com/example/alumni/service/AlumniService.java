package com.example.alumni.service;

import com.example.alumni.dto.AlumniNetworkResponse; // <-- NEW IMPORT
import com.example.alumni.dto.AlumniProfileRequest;
import com.example.alumni.dto.AlumniProfileResponse;
import com.example.alumni.dto.DigitalIdResponse;
import com.example.alumni.dto.DonationResponse;

import java.util.List;

public interface AlumniService {

    /**
     * Retrieves the profile of the currently authenticated alumnus.
     */
    AlumniProfileResponse getCurrentAlumniProfile();

    /**
     * Updates the profile of the currently authenticated alumnus.
     */
    AlumniProfileResponse updateCurrentAlumniProfile(AlumniProfileRequest request);

    /**
     * Retrieves the Digital ID for the currently authenticated alumnus.
     */
    DigitalIdResponse getDigitalIdForCurrentUser();

    /**
     * Retrieves the donation history for the currently authenticated alumnus.
     */
    List<DonationResponse> getDonationHistoryForCurrentUser();

    // --- THIS IS THE MISSING METHOD DECLARATION ---
    /**
     * Retrieves the network of alumni from the same college as the current user.
     * @return A list of alumni network profiles.
     */
    List<AlumniNetworkResponse> getAlumniNetwork();
}