package com.example.alumni.serviceimpl;

import com.example.alumni.dto.EventRequest;
import com.example.alumni.dto.EventResponse;
import com.example.alumni.dto.RsvpRequest;
import com.example.alumni.entity.College;
import com.example.alumni.entity.Event;
import com.example.alumni.entity.EventRsvp;
import com.example.alumni.entity.EventRsvpId;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.repository.AlumniProfileRepository;
import com.example.alumni.repository.CollegeRepository;
import com.example.alumni.repository.EventRepository;
import com.example.alumni.repository.EventRsvpRepository;
import com.example.alumni.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
private final CollegeRepository collegeRepository;
private final EventRsvpRepository eventRsvpRepository;
private final AlumniProfileRepository alumniProfileRepository;

@Override
@Transactional
public EventResponse createEvent(EventRequest request) {
    College college = collegeRepository.findById(request.getCollegeId())
            .orElseThrow(() -> new NotFoundException("College not found with id: " + request.getCollegeId()));

    Event event = Event.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .startTime(request.getStartTime())
            .endTime(request.getEndTime())
            .location(request.getLocation())
            .isVirtual(request.isVirtual())
            .college(college)
            .build();
    
    Event savedEvent = eventRepository.save(event);
    return mapToEventResponse(savedEvent);
}

@Override
public List<EventResponse> getAllEvents() {
    return eventRepository.findAll().stream()
            .map(this::mapToEventResponse)
            .collect(Collectors.toList());
}

@Override
@Transactional
public EventResponse updateEvent(String eventId, EventRequest request) {
    Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));
    
    event.setTitle(request.getTitle());
    event.setDescription(request.getDescription());
    event.setStartTime(request.getStartTime());
    event.setEndTime(request.getEndTime());
    event.setLocation(request.getLocation());
    event.setVirtual(request.isVirtual());
    
    Event updatedEvent = eventRepository.save(event);
    return mapToEventResponse(updatedEvent);
}

@Override
@Transactional
public void rsvpToEvent(String eventId, String alumniUserId, RsvpRequest rsvpRequest) {
    if (!eventRepository.existsById(eventId)) {
        throw new NotFoundException("Event not found with id: " + eventId);
    }
    if (!alumniProfileRepository.existsById(alumniUserId)) {
        throw new NotFoundException("Alumni profile not found with id: " + alumniUserId);
    }

    EventRsvpId rsvpId = new EventRsvpId();
    rsvpId.setEventId(eventId);
    rsvpId.setAlumniUserId(alumniUserId);
    
    EventRsvp rsvp = eventRsvpRepository.findById(rsvpId)
            .orElse(EventRsvp.builder()
                    .eventId(eventId)
                    .alumniUserId(alumniUserId)
                    .build());
    
    rsvp.setStatus(rsvpRequest.getStatus());
    eventRsvpRepository.save(rsvp);
}

/**
 * Maps an Event entity to an EventResponse DTO.
 * This is where the correction is made.
 */
private EventResponse mapToEventResponse(Event event) {
    return EventResponse.builder()
            .eventId(event.getEventId())
            .title(event.getTitle())
            .description(event.getDescription())
            .startTime(event.getStartTime())
            .endTime(event.getEndTime())
            .location(event.getLocation())
            .isVirtual(event.isVirtual())
            .createdAt(event.getCreatedAt())
            .collegeId(event.getCollege().getCollegeId()) // Correct
            .collegeName(event.getCollege().getName())     // Correct
            .build();
}

}