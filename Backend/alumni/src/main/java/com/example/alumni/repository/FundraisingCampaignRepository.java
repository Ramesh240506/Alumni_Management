package com.example.alumni.repository;

import com.example.alumni.entity.FundraisingCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundraisingCampaignRepository extends JpaRepository<FundraisingCampaign, String> {
}