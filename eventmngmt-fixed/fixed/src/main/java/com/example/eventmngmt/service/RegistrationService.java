package com.example.eventmngmt.service;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.entity.Event;
import com.example.eventmngmt.entity.Registration;
import com.example.eventmngmt.entity.User;
import com.example.eventmngmt.exception.DuplicateRegistrationException;
import com.example.eventmngmt.exception.EventAlreadyPassedException;
import com.example.eventmngmt.exception.EventFullException;
import com.example.eventmngmt.exception.EventNotFoundException;
import com.example.eventmngmt.exception.RegistrationNotFoundException;
import com.example.eventmngmt.exception.UserNotFoundException;
import com.example.eventmngmt.repository.EventRepository;
import com.example.eventmngmt.repository.RegistrationRepository;
import com.example.eventmngmt.repository.UserRepository;

// ── Spring Transaction ────────────────────────────────────────────────────────
import org.springframework.transaction.annotation.Transactional;

// ── Spring ────────────────────────────────────────────────────────────────────
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public Registration registerUserForEvent(Long userId, Long eventId) {

        // Load event — throws 404 if not found
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        // Load user — throws 404 if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Business rule 1 — cannot register for a past event
        if (!event.isUpcoming()) {
            throw new EventAlreadyPassedException("Cannot register for a past event.");
        }

        // Business rule 2 — event must still have capacity
        if (!event.hasCapacity()) {
            throw new EventFullException("Event '" + event.getName() + "' is fully booked.");
        }

        // Business rule 3 — user cannot register twice for the same event
        if (registrationRepository.existsByUserIdAndEventId(userId, eventId)) {
            throw new DuplicateRegistrationException("You are already registered for this event.");
        }

        // Create the registration record
        Registration reg = Registration.builder()
                .user(user)
                .event(event)
                .status(Registration.Status.CONFIRMED)
                .build();

        // Increment registered count on the event
        event.setRegisteredCount(event.getRegisteredCount() + 1);
        eventRepository.save(event);

        return registrationRepository.save(reg);
    }

    public void cancelRegistration(Long userId, Long eventId) {

        Registration reg = registrationRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new RegistrationNotFoundException(userId, eventId));

        // Mark as cancelled
        reg.setStatus(Registration.Status.CANCELLED);

        // Decrement registered count (never go below 0)
        Event event = reg.getEvent();
        event.setRegisteredCount(Math.max(0, event.getRegisteredCount() - 1));

        eventRepository.save(event);
        registrationRepository.save(reg);
    }
}
