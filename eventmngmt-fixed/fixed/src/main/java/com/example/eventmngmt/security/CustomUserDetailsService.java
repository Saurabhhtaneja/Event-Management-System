package com.example.eventmngmt.security;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.entity.User;
import com.example.eventmngmt.repository.UserRepository;

// ── Spring Security ───────────────────────────────────────────────────────────
// UserDetailsService is the interface Spring Security calls during login
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// ── Spring ────────────────────────────────────────────────────────────────────
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Spring Security calls this method with the username (email in our case)
    // during authentication to load the user from the database
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email));
        return new CustomUserDetails(user);
    }
}
