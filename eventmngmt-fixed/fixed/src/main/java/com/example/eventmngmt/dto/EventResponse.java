package com.example.eventmngmt.dto;

// ── Your entity ───────────────────────────────────────────────────────────────
import com.example.eventmngmt.entity.Event;

// ── Lombok ────────────────────────────────────────────────────────────────────
import lombok.Builder;
import lombok.Data;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.time.LocalDateTime;

@Data
@Builder
public class EventResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private String location;
    private Integer capacity;
    private Integer registeredCount;
    private Integer availableSpots;
    private String createdBy;

    // Static factory — converts Entity → DTO
    public static EventResponse from(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .capacity(event.getCapacity())
                .registeredCount(event.getRegisteredCount())
                .availableSpots(event.getCapacity() - event.getRegisteredCount())
                .createdBy(event.getCreatedBy() != null
                        ? event.getCreatedBy().getFullName() : null)
                .build();
    }
}
