package com.example.eventmngmt.repository;

// ── Your entity ───────────────────────────────────────────────────────────────
import com.example.eventmngmt.entity.User;

// ── Spring Data JPA ───────────────────────────────────────────────────────────
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
