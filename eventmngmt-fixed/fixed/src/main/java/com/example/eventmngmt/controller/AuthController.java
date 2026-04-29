package com.example.eventmngmt.controller;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.dto.JwtResponse;
import com.example.eventmngmt.dto.LoginRequest;
import com.example.eventmngmt.dto.RegisterRequest;
import com.example.eventmngmt.service.AuthService;

// ── Validation ────────────────────────────────────────────────────────────────
// @Valid triggers validation of @NotBlank, @Email, @Size annotations in the DTO
import jakarta.validation.Valid;

// ── Spring Web ────────────────────────────────────────────────────────────────
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ── Lombok ────────────────────────────────────────────────────────────────────
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // POST /api/auth/register  — public, no token needed
    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    // POST /api/auth/login  — public, no token needed
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
