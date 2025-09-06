package com.example.alumni.dto;

import com.example.alumni.entity.enums.CampaignStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CampaignRequest {
    @NotNull(message = "College ID cannot be null")
    private UUID collegeId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "Goal amount cannot be null")
    @DecimalMin(value = "0.01", message = "Goal amount must be greater than 0")
    private BigDecimal goalAmount;

    @NotNull(message = "Campaign status cannot be null")
    private CampaignStatus status;
}