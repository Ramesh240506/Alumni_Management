package com.example.alumni.service;

import com.example.alumni.dto.DonationRequest;
import com.example.alumni.dto.DonationResponse;

public interface DonationService {
    DonationResponse processDonation(DonationRequest request);
}