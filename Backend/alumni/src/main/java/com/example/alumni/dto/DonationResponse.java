package com.example.alumni.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class DonationResponse {
    private String donationId;
    private BigDecimal amount;
    private boolean isAnonymous;
    private LocalDateTime donatedAt;
    private CampaignInfo campaign;

    @Data
    @Builder
    public static class CampaignInfo {
        private String campaignId;
        private String title;
    }
}