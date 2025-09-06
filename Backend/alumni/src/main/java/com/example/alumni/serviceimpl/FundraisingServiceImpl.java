package com.example.alumni.serviceimpl;

import com.example.alumni.dto.CampaignRequest;
import com.example.alumni.dto.CampaignResponse;
import com.example.alumni.entity.College;
import com.example.alumni.entity.FundraisingCampaign;
import com.example.alumni.repository.CollegeRepository;
import com.example.alumni.repository.FundraisingCampaignRepository;
import com.example.alumni.service.FundraisingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundraisingServiceImpl implements FundraisingService {

    private final FundraisingCampaignRepository fundraisingCampaignRepository;
    private final CollegeRepository collegeRepository;
    @Override
    public CampaignResponse createCampaign(CampaignRequest campaignRequest) {
        // Fetch the College entity from DB using the collegeId from request
        College college = collegeRepository.findById(campaignRequest.getCollegeId())
                .orElseThrow(() -> new RuntimeException("College not found"));

        FundraisingCampaign campaign = FundraisingCampaign.builder()
                .college(college) // set College object, not collegeId
                .title(campaignRequest.getTitle())
                .goalAmount(campaignRequest.getGoalAmount())
                .status(campaignRequest.getStatus())
                .build();

        FundraisingCampaign savedCampaign = fundraisingCampaignRepository.save(campaign);
        return mapToCampaignResponse(savedCampaign);
    }


    @Override
    public List<CampaignResponse> getAllCampaigns() {
        return fundraisingCampaignRepository.findAll().stream()
                .map(this::mapToCampaignResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignResponse getCampaignById(UUID campaignId) {
        FundraisingCampaign campaign = fundraisingCampaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Fundraising campaign not found with ID: " + campaignId));
        return mapToCampaignResponse(campaign);
    }

    private CampaignResponse mapToCampaignResponse(FundraisingCampaign campaign) {
        return CampaignResponse.builder()
                .campaignId(campaign.getCampaignId())
                .collegeId(UUID.fromString(campaign.getCollege().getCollegeId()))
                .title(campaign.getTitle())
                .goalAmount(campaign.getGoalAmount())
                .status(campaign.getStatus())
                .build();
    }
}