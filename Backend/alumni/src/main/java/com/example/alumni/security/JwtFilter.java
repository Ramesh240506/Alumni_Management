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

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // This should run for every request
        System.out.println("\n--- [JWT FILTER] Processing request for: " + request.getRequestURI() + " ---");

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("--- [JWT FILTER] No JWT Token found. Passing to next filter. ---");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        String userEmail = null;

        try {
            userEmail = jwtUtil.extractUsername(jwt);
            System.out.println("--- [JWT FILTER] Extracted username from token: " + userEmail + " ---");
        } catch (Exception e) {
            // This will tell us if the token is expired or malformed
            System.err.println("--- [JWT FILTER] ERROR: Could not extract username. Token might be invalid. Error: " + e.getMessage() + " ---");
            filterChain.doFilter(request, response);
            return;
        }

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("--- [JWT FILTER] User '" + userEmail + "' is not yet authenticated. Loading details...");
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            try {
                if (jwtUtil.isTokenValid(jwt, userDetails)) {
                    System.out.println("--- [JWT FILTER] SUCCESS: Token is valid for user '" + userEmail + "'. Setting authentication context. ---");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                     System.err.println("--- [JWT FILTER] ERROR: Token is considered INVALID by isTokenValid() method. ---");
                }
            } catch (Exception e) {
                 // This will tell us if the signature is wrong
                 System.err.println("--- [JWT FILTER] ERROR: Exception during token validation. Secret key might be wrong. Error: " + e.getMessage() + " ---");
            }
        } else {
             System.out.println("--- [JWT FILTER] User is already authenticated. Skipping. ---");
        }

        filterChain.doFilter(request, response);
    }
}