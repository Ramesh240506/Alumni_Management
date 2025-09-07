package com.example.alumni.util;

import java.util.Map;

/**
 * A utility class for common operations on Maps.
 */
public class MapUtil {

    /**
     * Safely gets a String value from a map.
     *
     * @param map          The map to query.
     * @param key          The key to look for.
     * @param defaultValue The value to return if the key is not found or the value is not a String.
     * @return The String value or the default value.
     */
    public static String getString(Map<String, Object> map, String key, String defaultValue) {
        if (map == null || !map.containsKey(key)) {
            return defaultValue;
        }
        Object value = map.get(key);
        if (value instanceof String) {
            return (String) value;
        }
        return defaultValue;
    }
}