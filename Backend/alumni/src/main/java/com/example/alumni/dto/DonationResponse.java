package com.example.alumni.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponse {
    private UUID donationId;
    private BigDecimal amount;
    private boolean isAnonymous;
    private LocalDateTime donatedAt;
    private UUID donorUserId;
    private CampaignInfo campaign;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CampaignInfo {
        private UUID campaignId;
        private String title;
    }
}
