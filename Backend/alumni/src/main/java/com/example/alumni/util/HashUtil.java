package com.example.alumni.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility class for creating simple cryptographic hashes (e.g., SHA-256).
 * WARNING: This is NOT for hashing passwords. Use Spring Security's PasswordEncoder for that.
 */
public class HashUtil {

    /**
     * Creates a SHA-256 hash of a given string.
     *
     * @param input The string to hash.
     * @return The hexadecimal representation of the SHA-256 hash.
     */
    public static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}