package com.example.eventmngmt.security;

// ── JJWT (io.jsonwebtoken) ────────────────────────────────────────────────────
// jjwt-api gives you: Jwts, Claims, JwtException
// jjwt-impl (runtime) provides the implementations
// jjwt-jackson (runtime) handles JSON serialization
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// ── Spring ────────────────────────────────────────────────────────────────────
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// ── Java ──────────────────────────────────────────────────────────────────────
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Read from application.properties: app.jwt.secret
    @Value("${app.jwt.secret}")
    private String secret;

    // Read from application.properties: app.jwt.expiration-ms
    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    // Builds a SecretKey from the Base64-encoded secret string
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // Creates a signed JWT token with email as subject and role as a claim
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // Returns true if token signature is valid and not expired
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Parses and verifies the token, returns the claims payload
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
