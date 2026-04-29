package com.example.eventmngmt.service;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.dto.EventRequest;
import com.example.eventmngmt.entity.Event;
import com.example.eventmngmt.entity.User;
import com.example.eventmngmt.exception.EventNotFoundException;
import com.example.eventmngmt.exception.UserNotFoundException;
import com.example.eventmngmt.repository.EventRepository;
import com.example.eventmngmt.repository.UserRepository;

// ── Spring Data ───────────────────────────────────────────────────────────────
// Page = paginated result wrapper, Pageable = pagination parameters
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// ── Spring Transaction ────────────────────────────────────────────────────────
// @Transactional(readOnly=true) — optimises DB reads (no dirty-check overhead)
// @Transactional — required on write operations to auto-commit / rollback
import org.springframework.transaction.annotation.Transactional;

// ── Spring ────────────────────────────────────────────────────────────────────
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    // Returns upcoming events, optionally filtered by location
    public Page<Event> getUpcomingEvents(String location, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        if (location != null && !location.isBlank()) {
            return eventRepository
                    .findByEventDateAfterAndLocationContainingIgnoreCaseOrderByEventDateAsc(
                            now, location, pageable);
        }
        return eventRepository.findByEventDateAfterOrderByEventDateAsc(now, pageable);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Transactional
    public Event createEvent(EventRequest request, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new UserNotFoundException(creatorEmail));

        Event event = Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .eventDate(request.getEventDate())
                .location(request.getLocation())
                .capacity(request.getCapacity())
                .registeredCount(0)
                .createdBy(creator)
                .build();

        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new EventNotFoundException(id);
        }
        eventRepository.deleteById(id);
    }
}
