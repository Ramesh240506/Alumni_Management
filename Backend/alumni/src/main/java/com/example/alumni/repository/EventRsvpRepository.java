package com.example.alumni.repository;

import com.example.alumni.entity.EventRsvp;
import com.example.alumni.entity.EventRsvpId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRsvpRepository extends JpaRepository<EventRsvp, EventRsvpId> {
}