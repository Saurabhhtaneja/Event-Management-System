package com.example.eventmngmt.repository;

// ── Your entity ───────────────────────────────────────────────────────────────
import com.example.eventmngmt.entity.Registration;

// ── Spring Data JPA ───────────────────────────────────────────────────────────
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
    Optional<Registration> findByUserIdAndEventId(Long userId, Long eventId);
    List<Registration> findByUserId(Long userId);
}
