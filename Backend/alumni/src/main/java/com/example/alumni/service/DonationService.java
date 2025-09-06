package com.example.alumni.service;

import com.example.alumni.dto.DonationRequest;
import com.example.alumni.dto.DonationResponse;
import java.util.List;
import java.util.UUID;

public interface DonationService {
    DonationResponse donateToCampaign(UUID campaignId, DonationRequest donationRequest);
    List<DonationResponse> getMyDonations(UUID donorUserId);
    // You might add methods to view donations for a specific campaign as an admin
}