package com.example.alumni.service;

import com.example.alumni.dto.CampaignRequest;
import com.example.alumni.dto.CampaignResponse;
import java.util.List;
import java.util.UUID;

public interface FundraisingService {
    CampaignResponse createCampaign(CampaignRequest campaignRequest);
    List<CampaignResponse> getAllCampaigns();
    CampaignResponse getCampaignById(UUID campaignId);
    // You might add update and delete methods as needed
}