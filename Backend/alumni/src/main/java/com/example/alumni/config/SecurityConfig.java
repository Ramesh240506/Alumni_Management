package com.example.alumni.config;

import com.example.alumni.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main configuration class for Spring Security.
 * It defines the security filter chain, authentication providers, and other security-related beans.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService; // This is our UserDetailsServiceImpl

    /**
     * Defines the main security filter chain that applies to all incoming HTTP requests.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Enable CORS with default settings. This is crucial for Swagger UI and frontend apps.
            .cors(Customizer.withDefaults())
            
            // 2. Disable CSRF (Cross-Site Request Forgery) protection for stateless REST APIs.
            .csrf(AbstractHttpConfigurer::disable)

            // 3. Define authorization rules for different endpoints.
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/auth/**",              // Allow all requests to authentication endpoints.
                        "/swagger-ui/**",          // Allow access to the Swagger UI HTML page and its resources.
                        "/v3/api-docs/**"          // Allow access to the OpenAPI specification document.
                ).permitAll() // Mark the above paths as public.
                .anyRequest().authenticated() // All other requests must be authenticated.
            )

            // 4. Configure session management to be stateless, as we are using JWTs.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 5. Set the custom authentication provider.
            .authenticationProvider(authenticationProvider())

            // 6. Add our custom JWT filter to be executed before the standard username/password filter.
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            
            // 7. Explicitly enable and configure support for anonymous users.
            .anonymous(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Creates the AuthenticationProvider bean.
     * This provider uses our custom UserDetailsService to fetch user data and the PasswordEncoder to verify passwords.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Exposes the AuthenticationManager as a bean.
     * This is required by our AuthService to process login requests.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines the PasswordEncoder bean.
     * We use BCrypt, which is the industry standard for hashing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}