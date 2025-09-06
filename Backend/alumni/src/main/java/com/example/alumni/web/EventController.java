package com.example.alumni.web;

import com.example.alumni.dto.ApiResponse;
import com.example.alumni.dto.EventRequest;
import com.example.alumni.dto.EventResponse;
import com.example.alumni.dto.RsvpRequest;
import com.example.alumni.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/college/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest eventRequest) {
        EventResponse createdEvent = eventService.createEvent(eventRequest);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> listAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable("id") String eventId,
            @RequestBody EventRequest eventRequest) {
        EventResponse updatedEvent = eventService.updateEvent(eventId, eventRequest);
        return ResponseEntity.ok(updatedEvent);
    }

    @PostMapping("/{id}/rsvp")
    public ResponseEntity<ApiResponse> rsvpToEvent(
            @PathVariable("id") String eventId,
            @RequestBody RsvpRequest rsvpRequest) {
        
        // In a real application, you would get the authenticated user's ID
        // from the Spring Security context, not from a request parameter.
        String currentAlumniUserId = "user-placeholder-id"; // TODO: Replace with actual authenticated user ID

        eventService.rsvpToEvent(eventId, currentAlumniUserId, rsvpRequest);
        return ResponseEntity.ok(new ApiResponse("RSVP submitted successfully."));
    }
}