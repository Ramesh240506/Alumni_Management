package com.example.alumni.repository;

import com.example.alumni.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, String> {

    // Find all donations made by a specific user, identified by their user ID.
    List<Donation> findByDonor_UserIdOrderByDonatedAtDesc(String userId);
}