package com.example.alumni.serviceimpl;

import com.example.alumni.dto.EventRequest;
import com.example.alumni.dto.EventResponse;
import com.example.alumni.entity.*;
import com.example.alumni.entity.enums.RsvpStatus;
import com.example.alumni.entity.enums.UserRole;
import com.example.alumni.exception.NotFoundException;
import com.example.alumni.exception.UnauthorizedException;
import com.example.alumni.repository.*;
import com.example.alumni.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventRsvpRepository eventRsvpRepository;
    private final UserRepository userRepository;
    private final CollegeRepository collegeRepository;

    @Override
    public List<EventResponse> getEventsForCollege() {
        User currentUser = getCurrentUser();
        String collegeId;

        if (currentUser.getRole() == UserRole.ALUMNI) {
            collegeId = currentUser.getAlumniProfile().getCollege().getCollegeId();
        } else if (currentUser.getRole() == UserRole.COLLEGE_ADMIN) {
            collegeId = currentUser.getAffiliations().stream().findFirst()
                    .orElseThrow(() -> new IllegalStateException("College admin is not affiliated with any college."))
                    .getEntityId();
        } else {
            throw new UnauthorizedException("User role is not authorized to view events.");
        }
        
        return eventRepository.findByCollege_CollegeIdOrderByStartTimeDesc(collegeId)
                .stream()
                .map(this::mapToEventResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventResponse createEvent(EventRequest eventRequest) {
        User currentUser = getCurrentUser();
        
        // Authorization Check: Only College Admins can create events.
        if (currentUser.getRole() != UserRole.COLLEGE_ADMIN) {
            throw new UnauthorizedException("Only College Administrators can create events.");
        }

        String collegeId = currentUser.getAffiliations().stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("College admin is not affiliated with any college."))
                .getEntityId();
        
        College college = collegeRepository.findById(collegeId)
                .orElseThrow(() -> new NotFoundException("Affiliated college not found."));

        Event newEvent = new Event();
        newEvent.setTitle(eventRequest.getTitle());
        newEvent.setDescription(eventRequest.getDescription());
        newEvent.setStartTime(eventRequest.getStartTime());
        newEvent.setEndTime(eventRequest.getEndTime());
        newEvent.setLocation(eventRequest.getLocation());
        newEvent.setVirtual(eventRequest.isVirtual());
        newEvent.setCollege(college);

        Event savedEvent = eventRepository.save(newEvent);
        return mapToEventResponse(savedEvent);
    }

    @Override
    @Transactional
    public void rsvpToEvent(String eventId, RsvpStatus status) {
        User currentUser = getCurrentUser();
        
        // Authorization Check: Only Alumni can RSVP.
        if (currentUser.getRole() != UserRole.ALUMNI) {
            throw new UnauthorizedException("Only alumni can RSVP to events.");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with ID: " + eventId));
        
        // Business Rule: Ensure alumnus belongs to the college hosting the event.
        if (!event.getCollege().getCollegeId().equals(currentUser.getAlumniProfile().getCollege().getCollegeId())) {
            throw new UnauthorizedException("You can only RSVP to events hosted by your own college.");
        }

        EventRsvp rsvp = new EventRsvp();
        rsvp.setEventId(eventId);
        rsvp.setAlumniUserId(currentUser.getUserId());
        rsvp.setStatus(status);
        rsvp.setEvent(event);
        rsvp.setAlumniProfile(currentUser.getAlumniProfile());
        
        eventRsvpRepository.save(rsvp);
    }
    
    // Helper method to get the authenticated user
    private User getCurrentUser() {
        // ... (this method is the same as in previous services)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
             throw new IllegalStateException("User is not authenticated.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found from security context."));
    }
    
    // Helper method for DTO mapping
    private EventResponse mapToEventResponse(Event event) {
        EventResponse.CollegeInfo collegeInfo = EventResponse.CollegeInfo.builder()
                .collegeId(event.getCollege().getCollegeId())
                .name(event.getCollege().getName())
                .build();
                
        return EventResponse.builder()
                .eventId(event.getEventId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .location(event.getLocation())
                .isVirtual(event.isVirtual())
                .college(collegeInfo)
                .build();
    }
}