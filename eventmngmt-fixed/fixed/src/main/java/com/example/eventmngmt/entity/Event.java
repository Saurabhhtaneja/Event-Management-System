package com.example.eventmngmt.entity;

// ── JPA annotations ───────────────────────────────────────────────────────────
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// ── Validation annotations ────────────────────────────────────────────────────
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;

// ── Lombok ────────────────────────────────────────────────────────────────────
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ── Hibernate ─────────────────────────────────────────────────────────────────
import org.hibernate.annotations.CreationTimestamp;

// ── Jackson ───────────────────────────────────────────────────────────────────
import com.fasterxml.jackson.annotation.JsonIgnore;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer registeredCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();

    public boolean hasCapacity() {
        return registeredCount < capacity;
    }

    public boolean isUpcoming() {
        return eventDate.isAfter(LocalDateTime.now());
    }
}
