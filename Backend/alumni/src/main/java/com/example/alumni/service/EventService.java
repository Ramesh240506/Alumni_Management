package com.example.alumni.service;

import com.example.alumni.dto.EventRequest;
import com.example.alumni.dto.EventResponse;
import com.example.alumni.dto.RsvpRequest;

import java.util.List;

public interface EventService {
    EventResponse createEvent(EventRequest eventRequest);
    List<EventResponse> getAllEvents();
    EventResponse updateEvent(String eventId, EventRequest eventRequest);
    void rsvpToEvent(String eventId, String alumniUserId, RsvpRequest rsvpRequest);
}