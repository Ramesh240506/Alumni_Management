package com.example.alumni.repository;

import com.example.alumni.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface DonationRepository extends JpaRepository<Donation, UUID> {
    
    // Find all donations made by a specific user
    List<Donation> findByDonorUserId(UUID donorUserId);
    
    // Find all donations for a specific campaign
    List<Donation> findByCampaignId(UUID campaignId);
}
