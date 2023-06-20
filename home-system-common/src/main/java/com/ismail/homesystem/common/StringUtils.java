package com.ismail.homesystem.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
    public static FormattedLocation parseString(String input) {
        Pattern pattern = Pattern.compile("world:(.*?)x:(.*?)y:(.*?)z:(.*?)");

        // Match the pattern against the input string
        Matcher matcher = pattern.matcher(input);

        // Check if the pattern matches the input
        if (matcher.matches()) {
            // Extract the captured groups using the group(index) method
            String world = matcher.group(1);
            int x = Integer.parseInt(matcher.group(2));
            int y = Integer.parseInt(matcher.group(3));
            int z = Integer.parseInt(matcher.group(4));

            // Create a Result object with the extracted values
            return new FormattedLocation(world, x, y, z);
        } else {
            return null; // Return null if the input format is invalid
        }
    }

    public static String generateLocationString(FormattedLocation formattedLocation) {
        // Generate the formatted string using the provided values
        return String.format("world:%sx:%dy:%dz:%d", formattedLocation.world, formattedLocation.x, formattedLocation.y, formattedLocation.z);
    }

    // A simple class to hold the extracted values
        public record FormattedLocation(String world, int x, int y, int z) {
    }
}