package com.example.alumni.entity;

import com.example.alumni.entity.enums.CampaignStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a fundraising campaign initiated by a college to raise funds from alumni.
 */
@Entity
@Table(name = "Fundraising_Campaigns")
@Getter
@Setter
public class FundraisingCampaign {

    @Id
    @Column(name = "campaignId", length = 36, nullable = false, updatable = false)
    private String campaignId;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "goalAmount", nullable = false, precision = 15, scale = 2)
    private BigDecimal goalAmount;

    @Column(name = "currentAmount", nullable = false, precision = 15, scale = 2, columnDefinition = "DECIMAL(15,2) DEFAULT 0.00")
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CampaignStatus status = CampaignStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // --- Relationships ---

    /**
     * The College that is running this fundraising campaign.
     * A campaign must belong to one college.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collegeId", nullable = false)
    private College college;

    /**
     * The set of all donations made to this campaign.
     * This is the "one" side of the One-to-Many relationship with Donation.
     * CascadeType.ALL: If a campaign is deleted, all its associated donation records are also deleted.
     * orphanRemoval = true: Ensures that if a donation is removed from this set, it's deleted from the DB.
     */
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Donation> donations;


    @PrePersist
    public void prePersist() {
        if (this.campaignId == null) {
            this.campaignId = UUID.randomUUID().toString();
        }
    }
}