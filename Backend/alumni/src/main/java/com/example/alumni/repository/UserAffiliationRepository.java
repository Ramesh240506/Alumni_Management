package com.example.alumni.repository;

import com.example.alumni.entity.UserAffiliation;
import com.example.alumni.entity.UserAffiliationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAffiliationRepository extends JpaRepository<UserAffiliation, UserAffiliationId> {
}