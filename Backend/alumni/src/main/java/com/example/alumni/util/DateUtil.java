package com.example.alumni.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A utility class for common date and time operations.
 */
public class DateUtil {

    private static final DateTimeFormatter FRIENDLY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm a");

    /**
     * Formats a LocalDateTime object into a user-friendly string.
     * Example: "Sep 06, 2025 at 10:30 AM"
     *
     * @param dateTime The LocalDateTime to format.
     * @return A formatted string, or an empty string if the input is null.
     */
    public static String formatFriendly(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(FRIENDLY_FORMATTER);
    }
}