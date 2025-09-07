package com.example.alumni.web;

import com.example.alumni.dto.EventRequest;
import com.example.alumni.dto.EventResponse;
import com.example.alumni.entity.enums.RsvpStatus;
import com.example.alumni.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> getCollegeEvents() {
        // Service will get the college from the authenticated user
        return ResponseEntity.ok(eventService.getEventsForCollege());
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        // This endpoint will require a COLLEGE_ADMIN role
        EventResponse createdEvent = eventService.createEvent(eventRequest);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PostMapping("/{eventId}/rsvp")
    public ResponseEntity<Void> rsvpToEvent(@PathVariable String eventId, @RequestParam RsvpStatus status) {
        // This endpoint will require an ALUMNI role
        eventService.rsvpToEvent(eventId, status);
        return ResponseEntity.ok().build();
    }
}