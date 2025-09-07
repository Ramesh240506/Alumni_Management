package com.example.alumni.dto;

import com.example.alumni.entity.enums.CampaignStatus;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CampaignResponse {
    private String campaignId;
    private String title;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignStatus status;
}