package com.example.alumni.service;

import com.example.alumni.dto.EventRequest;
import com.example.alumni.dto.EventResponse;
import com.example.alumni.entity.enums.RsvpStatus;

import java.util.List;

public interface EventService {
    List<EventResponse> getEventsForCollege();
    EventResponse createEvent(EventRequest eventRequest);
    void rsvpToEvent(String eventId, RsvpStatus status);
}