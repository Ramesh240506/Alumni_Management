package com.example.alumni.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CampaignRequest {
    @NotBlank
    private String title;
    private String description;
    @NotNull
    @DecimalMin("1.00")
    private BigDecimal goalAmount;
    private LocalDate startDate;
    private LocalDate endDate;
}