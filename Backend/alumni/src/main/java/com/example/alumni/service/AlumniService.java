package com.example.alumni.service;

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
     * @param request DTO containing the fields to update.
     * @return The updated alumni profile.
     */
    AlumniProfileResponse updateCurrentAlumniProfile(AlumniProfileRequest request);

    /**
     * Retrieves the Digital ID for the currently authenticated alumnus.
     * @return A DTO containing the Digital ID information.
     */
    DigitalIdResponse getDigitalIdForCurrentUser();

    /**
     * Retrieves the donation history for the currently authenticated alumnus.
     * @return A list of DTOs representing the user's past donations.
     */
    List<DonationResponse> getDonationHistoryForCurrentUser();
}