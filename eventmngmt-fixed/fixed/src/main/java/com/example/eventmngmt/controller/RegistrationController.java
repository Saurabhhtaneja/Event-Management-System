package com.example.eventmngmt.controller;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.security.CustomUserDetails;
import com.example.eventmngmt.service.RegistrationService;

// ── Spring Security ───────────────────────────────────────────────────────────
// Authentication holds the logged-in user — we cast getPrincipal() to CustomUserDetails
// to access the user's database ID
import org.springframework.security.core.Authentication;

// ── Spring Web ────────────────────────────────────────────────────────────────
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ── Lombok ────────────────────────────────────────────────────────────────────
import lombok.RequiredArgsConstructor;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.util.Map;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // POST /api/registrations/events/1
    // Requires a valid JWT token (any role)
    @PostMapping("/events/{eventId}")
    public ResponseEntity<Map<String, String>> register(
            @PathVariable Long eventId,
            Authentication authentication) {

        // Cast principal to CustomUserDetails to get the user's DB id
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        registrationService.registerUserForEvent(userId, eventId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "Successfully registered for the event."));
    }

    // DELETE /api/registrations/events/1
    // Requires a valid JWT token (any role)
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> cancel(
            @PathVariable Long eventId,
            Authentication authentication) {

        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        registrationService.cancelRegistration(userId, eventId);

        return ResponseEntity.noContent().build();
    }
}
