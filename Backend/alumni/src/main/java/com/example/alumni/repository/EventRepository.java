package com.example.alumni.repository;

import com.example.alumni.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    // Find all events for a specific college, ordered by the most recent start time.
    List<Event> findByCollege_CollegeIdOrderByStartTimeDesc(String collegeId);
}