package com.example.eventmngmt.exception;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(Long userId, Long eventId) {
        super("No registration found for user " + userId + " and event " + eventId);
    }
}
