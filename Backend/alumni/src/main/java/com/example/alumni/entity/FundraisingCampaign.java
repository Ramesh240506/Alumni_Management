package com.example.alumni.entity;

import com.example.alumni.entity.enums.CampaignStatus;
import com.example.alumni.entity.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundraisingCampaign {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID campaignId;

//    @Column(columnDefinition = "BINARY(16)")
//    private UUID collegeId; // FK to College

    private String title;
    private BigDecimal goalAmount;

    @ManyToOne
    @JoinColumn(name = "college_id")
    private College college;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status; // ENUM: CampaignStatus
}