package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;

public class Config {
    private static final String dateTimePattern = "dd-MM-yyyy HH:mm";
    private static final String datePattern = "dd-MM-yyyy";
    private static final String timePattern = "HH:mm";
    private static final String slotSize = "60";
    private static final String startSlotTime = "00:00";
    private static final String endSlotTime = "24:00";

    private static final String CONFIG_PATH = "UDCalculator.config";
    private static Config config = null;

    private final Properties properties;

    private Config() {
        properties = new Properties();
        properties.setProperty("DATE_TIME_PATTERN", dateTimePattern);
        properties.setProperty("DATE_PATTERN", datePattern);
        properties.setProperty("TIME_PATTERN", timePattern);
        properties.setProperty("SLOT_SIZE", slotSize);
        properties.setProperty("START_SLOT_TIME", startSlotTime);
        properties.setProperty("END_SLOT_TIME", endSlotTime);

        try {
            properties.load(Files.newInputStream(Path.of(CONFIG_PATH)));

            if (!isValidDateTimePattern(properties.getProperty("DATE_TIME_PATTERN"))) {
                properties.setProperty("DATE_TIME_PATTERN", dateTimePattern);
            }

            if (!isValidDatePattern(properties.getProperty("DATE_PATTERN"))) {
                properties.setProperty("DATE_PATTERN", datePattern);
            }

            if (!isValidTimePattern(properties.getProperty("TIME_PATTERN"))) {
                properties.setProperty("TIME_PATTERN", timePattern);
            }

            if(!isValidSlotSize(properties.getProperty("SLOT_SIZE"))) {
                properties.setProperty("SLOT_SIZE", slotSize);
            }

            if (!isValidSlotsTime(properties.getProperty("START_SLOT_TIME"), properties.getProperty("END_SLOT_TIME"))) {
                properties.setProperty("START_SLOT_TIME", startSlotTime);
                properties.setProperty("END_SLOT_TIME", endSlotTime);
            }
        } catch (IOException e) {
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Defaults");
            } catch (IOException ignored) {
            }
        }
    }

    private boolean isValidDateTimePattern(String pattern) {
        boolean isValid;
        try {
            DateTimeFormatter.ofPattern(pattern);
            isValid = pattern.contains("d") && pattern.contains("M") && pattern.contains("y") && pattern.contains("H") && pattern.contains("m");
        } catch (IllegalArgumentException e) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidDatePattern(String pattern) {
        boolean isValid;
        try {
            DateTimeFormatter.ofPattern(pattern);
            isValid = pattern.contains("d") && pattern.contains("M") && pattern.contains("y") && !pattern.contains("H") && !pattern.contains("m");
        } catch (IllegalArgumentException e) {
            isValid =  false;
        }
        return isValid;
    }

    private boolean isValidTimePattern(String pattern) {
        boolean isValid;
        try {
            DateTimeFormatter.ofPattern(pattern);
            isValid = !pattern.contains("d") && !pattern.contains("M") && !pattern.contains("y") && pattern.contains("H") && pattern.contains("m");
        } catch (IllegalArgumentException e) {
            isValid =  false;
        }
        return isValid;
    }

    private boolean isValidSlotSize(String pattern) {
        boolean isValid;
        try {
            int slotSize = Integer.valueOf(pattern);
            isValid = slotSize > 0 && slotSize < 1441;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isValidSlotsTime(String startSlotTime, String endSlotTime) {
        boolean isValid;
        try {
            isValid = LocalTime.parse(startSlotTime, DateTimeFormatter.ofPattern("HH:mm")).isBefore(LocalTime.parse(endSlotTime, DateTimeFormatter.ofPattern("HH:mm")));
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    public static Config getInstance() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }
}
