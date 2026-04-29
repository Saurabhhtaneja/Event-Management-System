package com.example.eventmngmt.repository;

// ── Your entity ───────────────────────────────────────────────────────────────
import com.example.eventmngmt.entity.Event;

// ── Spring Data JPA ───────────────────────────────────────────────────────────
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByEventDateAfterOrderByEventDateAsc(
            LocalDateTime date, Pageable pageable);

    Page<Event> findByEventDateAfterAndLocationContainingIgnoreCaseOrderByEventDateAsc(
            LocalDateTime date, String location, Pageable pageable);
}
