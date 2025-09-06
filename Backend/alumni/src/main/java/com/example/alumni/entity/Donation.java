package com.example.alumni.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a single donation made by a user towards a fundraising campaign.
 * This is a transactional record.
 */
@Entity
@Table(name = "Donations")
@Getter
@Setter
public class Donation {

    @Id
    @Column(name = "donationId", length = 36, nullable = false, updatable = false)
    private String donationId;

    /**
     * The monetary amount of the donation. Using BigDecimal is essential for financial calculations
     * to avoid floating-point inaccuracies.
     */
    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    /**
     * The unique transaction ID from the payment gateway (e.g., Stripe, PayPal)
     * for auditing and reference purposes.
     */
    @Column(name = "payment_gateway_tx_id", length = 255, nullable = false)
    private String paymentGatewayTxId;

    /**
     * A flag to indicate if the donor wishes for their name to be hidden publicly.
     */
    @Column(name = "is_anonymous", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isAnonymous = false;

    /**
     * The timestamp when the donation was successfully processed.
     * Automatically set when the entity is first persisted.
     */
    @CreationTimestamp
    @Column(name = "donated_at", nullable = false, updatable = false)
    private LocalDateTime donatedAt;

    // --- Relationships ---

    /**
     * The fundraising campaign to which this donation belongs. This is the "owning" side
     * of the One-to-Many relationship defined in FundraisingCampaign.
     * A donation must always be linked to a campaign.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaignId", nullable = false)
    private FundraisingCampaign campaign;

    /**
     * The user who made the donation.
     * The relationship is optional (nullable) to support cases where a user account might be deleted
     * but the donation record needs to be preserved. The database foreign key should be ON DELETE SET NULL.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donorUserId") // As per the full MySQL schema, it links to User
    private User donor;


    @PrePersist
    public void prePersist() {
        if (this.donationId == null) {
            this.donationId = UUID.randomUUID().toString();
        }
    }


}