package com.example.eventmngmt.security;

// ── Your classes ──────────────────────────────────────────────────────────────
import com.example.eventmngmt.security.CustomUserDetailsService;
import com.example.eventmngmt.security.JwtUtil;

// ── Jakarta Servlet ───────────────────────────────────────────────────────────
// OncePerRequestFilter ensures this filter runs exactly once per HTTP request
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// ── Spring Security ───────────────────────────────────────────────────────────
// SecurityContextHolder stores the authenticated user for the current request
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

// ── Spring ────────────────────────────────────────────────────────────────────
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;

// ── Java ──────────────────────────────────────────────────────────────────────
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Read the Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. If missing or doesn't start with "Bearer ", skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token (strip "Bearer " prefix)
        String token = authHeader.substring(7);

        // 4. Validate the token — if invalid, skip (request will fail at security layer)
        if (!jwtUtil.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Extract email from token
        String email = jwtUtil.extractEmail(token);

        // 6. If email exists and no authentication is set yet for this request
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 7. Load full user details from DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // 8. Build an authentication token with user's authorities
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            // 9. Attach request details (IP address, session, etc.)
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            // 10. Store authentication in SecurityContext so Spring Security
            //     knows this request is authenticated
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 11. Continue to next filter / controller
        filterChain.doFilter(request, response);
    }
}
