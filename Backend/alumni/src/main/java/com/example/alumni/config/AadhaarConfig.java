package com.example.alumni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for external service integrations, such as Aadhaar.
 * This is a good place to define beans like RestTemplate or WebClient that
 * might be needed to communicate with the Aadhaar verification service.
 */
@Configuration
public class AadhaarConfig {

    /**
     * Provides a RestTemplate bean that can be injected into services
     * like AadhaarOtpValidator for making HTTP requests to external APIs.
     * @return A RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}