package com.example.alumni.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * A filter that intercepts every HTTP request to validate the JWT token.
 * It ignores public paths and sets the user's authentication in the security context
 * if a valid token is found.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // A list of path prefixes that should be publicly accessible without a JWT.
    private static final List<String> PUBLIC_PATH_PREFIXES = Arrays.asList(
        "/auth",
        "/swagger-ui",
        "/v3/api-docs"
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Get the path of the request, excluding the application's context path.
        final String path = request.getRequestURI().substring(request.getContextPath().length());

        // Check if the request path starts with any of the public prefixes.
        boolean isPublicPath = PUBLIC_PATH_PREFIXES.stream().anyMatch(path::startsWith);
        if (isPublicPath) {
            filterChain.doFilter(request, response); // If it's public, skip the rest of the filter.
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // --- THIS IS THE FIX ---
        // If there is no token, pass the request to the next filter AND STOP.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // <<<<<<<<<<<<<<<< ADD THIS LINE
        }

        // If there's no "Bearer" token, pass the request on. The AuthorizationFilter later will deny access.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token and user's email.
        jwt = authHeader.substring(7);
        userEmail = jwtUtil.extractUsername(jwt);

        // If a user is found and is not already authenticated in the current context...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            // ... and if the token is valid for this user...
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                // ... create an authentication token and set it in the Security Context.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credentials are not needed as we use the token
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // Pass the request on to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}