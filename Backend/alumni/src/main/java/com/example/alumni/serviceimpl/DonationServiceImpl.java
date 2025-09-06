package com.example.alumni.serviceimpl;

import com.example.alumni.dto.DonationRequest;
import com.example.alumni.dto.DonationResponse;
import com.example.alumni.entity.Donation;
import com.example.alumni.entity.FundraisingCampaign;
import com.example.alumni.entity.User;
import com.example.alumni.repository.DonationRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.repository.FundraisingCampaignRepository;
import com.example.alumni.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final FundraisingCampaignRepository fundraisingCampaignRepository;
    private final UserRepository userRepository;
    @Override
    public DonationResponse donateToCampaign(UUID campaignId, DonationRequest donationRequest) {
        FundraisingCampaign campaign = fundraisingCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Fundraising campaign not found with ID: " + campaignId));
        User donorUser = userRepository.findById(donationRequest.getDonorUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donation donation = Donation.builder()
                .campaign(campaign)
                .donor(donorUser)
                .amount(donationRequest.getAmount())
                .build();

        Donation savedDonation = donationRepository.save(donation);
        return mapToDonationResponse(savedDonation);

    }

    @Override
    public List<DonationResponse> getMyDonations(UUID donorUserId) {
        return donationRepository.findByDonorUserId(donorUserId).stream()
                .map(this::mapToDonationResponse)
                .collect(Collectors.toList());
    }

    private DonationResponse mapToDonationResponse(Donation donation) {
        return DonationResponse.builder()
                .donationId(UUID.fromString(donation.getDonationId()))
                .campaignId(donation.getCampaign().getCampaignId())
                .donorUserId(UUID.fromString(donation.getDonationId()))
                .amount(donation.getAmount())
                .build();
    }
}