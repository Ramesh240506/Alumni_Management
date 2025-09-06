package com.example.alumni.repository;

import com.example.alumni.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {
    List<Donation> findByDonorUserId(UUID donorUserId);
    List<Donation> findByCampaignId(UUID campaignId);
}