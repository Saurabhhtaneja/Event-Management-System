package com.example.eventmngmt.security;

// ── Your entity ───────────────────────────────────────────────────────────────
import com.example.eventmngmt.entity.User;

// ── Spring Security ───────────────────────────────────────────────────────────
// UserDetails is the interface Spring Security uses to hold auth info
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// ── Lombok ────────────────────────────────────────────────────────────────────
import lombok.Getter;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id          = user.getId();
        this.email       = user.getEmail();
        this.password    = user.getPasswordHash();
        // "ROLE_USER" or "ROLE_ADMIN" — Spring Security expects the ROLE_ prefix
        this.authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    // Spring Security uses getUsername() to identify the principal
    @Override
    public String getUsername() { return email; }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}
