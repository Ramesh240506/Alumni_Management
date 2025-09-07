package com.example.alumni.serviceimpl;

import com.example.alumni.dto.DonationRequest;
import com.example.alumni.dto.DonationResponse;
import com.example.alumni.entity.Donation;
import com.example.alumni.entity.FundraisingCampaign;
import com.example.alumni.entity.User;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.DonationRepository;
import com.example.alumni.repository.FundraisingCampaignRepository;
import com.example.alumni.repository.UserRepository;
import com.example.alumni.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final FundraisingCampaignRepository campaignRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public DonationResponse processDonation(DonationRequest request) {
        User currentUser = getCurrentUser();
        
        FundraisingCampaign campaign = campaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new NotFoundException("Fundraising campaign not found with ID: " + request.getCampaignId()));

        // In a real application, you would integrate with a payment gateway here
        // using the request.getPaymentGatewayToken().
        // For now, we'll simulate a successful transaction.
        String transactionId = "txn_" + System.currentTimeMillis();
        
        Donation donation = new Donation();
        donation.setCampaign(campaign);
        donation.setDonor(currentUser);
        donation.setAmount(request.getAmount());
        donation.setPaymentGatewayTxId(transactionId);
        donation.setAnonymous(request.isAnonymous());
        
        // Update the campaign's current amount
        campaign.setCurrentAmount(campaign.getCurrentAmount().add(request.getAmount()));
        campaignRepository.save(campaign);
        
        Donation savedDonation = donationRepository.save(donation);
        
        return mapToDonationResponse(savedDonation);
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
    
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found."));
    }
}