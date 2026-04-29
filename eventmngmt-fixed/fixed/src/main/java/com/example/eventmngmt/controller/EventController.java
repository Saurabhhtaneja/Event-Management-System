package com.example.eventmngmt.controller;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.dto.EventRequest;
import com.example.eventmngmt.dto.EventResponse;
import com.example.eventmngmt.entity.Event;
import com.example.eventmngmt.service.EventService;

// ── Validation ────────────────────────────────────────────────────────────────
import jakarta.validation.Valid;

// ── Spring Security ───────────────────────────────────────────────────────────
// @PreAuthorize checks the role BEFORE the method runs
import org.springframework.security.access.prepost.PreAuthorize;
// Authentication holds the currently logged-in user's info
import org.springframework.security.core.Authentication;

// ── Spring Data ───────────────────────────────────────────────────────────────
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

// ── Spring Web ────────────────────────────────────────────────────────────────
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// ── Lombok ────────────────────────────────────────────────────────────────────
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // GET /api/events?location=Delhi&page=0&size=10
    // Public — no token needed
    @GetMapping
    public ResponseEntity<Page<EventResponse>> getUpcomingEvents(
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<EventResponse> response = eventService
                .getUpcomingEvents(location, pageable)
                .map(EventResponse::from);
        return ResponseEntity.ok(response);
    }

    // GET /api/events/1
    // Public — no token needed
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(EventResponse.from(eventService.getEventById(id)));
    }

    // POST /api/events
    // ADMIN only — requires Authorization: Bearer <token> with ROLE_ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> createEvent(
            @Valid @RequestBody EventRequest request,
            Authentication authentication) {

        // authentication.getName() returns the email stored in the JWT subject
        Event event = eventService.createEvent(request, authentication.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EventResponse.from(event));
    }

    // DELETE /api/events/1
    // ADMIN only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
