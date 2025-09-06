package com.example.alumni.repository;

import com.example.alumni.entity.FundraisingCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FundraisingCampaignRepository extends JpaRepository<FundraisingCampaign, UUID> {
}