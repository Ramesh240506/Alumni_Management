package com.example.alumni.dto;

import com.example.alumni.entity.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignResponse {
    private UUID campaignId;
    private UUID collegeId;
    private String title;
    private BigDecimal goalAmount;
    private CampaignStatus status;
}