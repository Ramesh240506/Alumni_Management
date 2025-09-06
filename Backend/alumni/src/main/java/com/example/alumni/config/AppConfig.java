package com.example.alumni.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // <-- IMPORTANT IMPORT
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides application-wide beans and configurations.
 */
@Configuration
public class AppConfig {

    /**
     * Provides a singleton instance of ObjectMapper and explicitly registers the JavaTimeModule.
     * This is the definitive fix for JSON serialization issues with java.time objects like
     * LocalDateTime and LocalDate.
     *
     * @return A configured ObjectMapper bean.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // This line explicitly teaches Jackson how to handle Java 8+ time objects.
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}