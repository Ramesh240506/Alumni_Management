package com.example.alumni.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DonationRequest {
    @NotBlank
    private String campaignId;
    @NotNull
    @DecimalMin("1.00")
    private BigDecimal amount;
    @NotBlank
    private String paymentGatewayToken; // e.g., a token from Stripe or PayPal
    private boolean isAnonymous = false;
}