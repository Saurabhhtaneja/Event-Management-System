package com.example.eventmngmt.service;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.dto.JwtResponse;
import com.example.eventmngmt.dto.LoginRequest;
import com.example.eventmngmt.dto.RegisterRequest;
import com.example.eventmngmt.entity.User;
import com.example.eventmngmt.exception.EmailAlreadyExistsException;
import com.example.eventmngmt.repository.UserRepository;
import com.example.eventmngmt.security.JwtUtil;

// ── Spring Security ───────────────────────────────────────────────────────────
// AuthenticationManager.authenticate() verifies email + password against the DB
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// PasswordEncoder hashes passwords with BCrypt before storing
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// ── Spring ────────────────────────────────────────────────────────────────────
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;      // BCryptPasswordEncoder from SecurityConfig
    private final AuthenticationManager authManager;    // AuthenticationManager from SecurityConfig
    private final JwtUtil jwtUtil;

    public JwtResponse register(RegisterRequest request) {

        // Check email is not already taken
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered.");
        }

        // Build and save the new user
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(User.Role.USER)
                .build();

        userRepository.save(user);

        // Generate JWT and return it
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new JwtResponse(token, user.getEmail(), user.getRole().name());
    }

    public JwtResponse login(LoginRequest request) {

        // This throws BadCredentialsException if email/password don't match
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );

        // If we reach here, credentials are valid — load user and generate token
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new JwtResponse(token, user.getEmail(), user.getRole().name());
    }
}
