package com.example.eventmngmt.dto;

// ── Validation annotations ────────────────────────────────────────────────────
// These come from spring-boot-starter-validation → jakarta.validation
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// ── Lombok ────────────────────────────────────────────────────────────────────
import lombok.Data;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.time.LocalDateTime;

@Data
public class EventRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDate;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}
