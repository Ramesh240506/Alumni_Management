package com.example.alumni.service;

import com.example.alumni.dto.CampaignRequest;
import com.example.alumni.dto.CampaignResponse;
import java.util.List;

public interface FundraisingService {
    List<CampaignResponse> getAllCampaigns();
    CampaignResponse createCampaign(CampaignRequest request);
}