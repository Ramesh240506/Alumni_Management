package com.example.alumni.util;

import org.springframework.stereotype.Component;

/**
 * A utility component for sending emails.
 * For the hackathon, this simulates sending an email by printing to the console.
 */
@Component
public class EmailUtil {

    /**
     * Simulates sending an email.
     *
     * @param to      The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The HTML or text content of the email.
     */
    public void sendEmail(String to, String subject, String body) {
        System.out.println("\n--- SIMULATING EMAIL ---");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("--- END OF EMAIL SIMULATION ---\n");
    }
}