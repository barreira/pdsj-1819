package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.Properties;

public class Config {
    private static final SimpleEntry<String, String> dateTimePattern =
            new SimpleEntry<>("DATE_TIME_PATTERN", "dd-MM-yyyy HH:mm");
    private static final SimpleEntry<String, String> datePattern =
            new SimpleEntry<>("DATE_PATTERN", "dd-MM-yyyy");
    private static final SimpleEntry<String, String> timePattern =
            new SimpleEntry<>("TIME_PATTERN", "HH:mm");
    private static final SimpleEntry<String, String> slotSize =
            new SimpleEntry<>("SLOT_SIZE", "60");
    private static final SimpleEntry<String, String> startSlotTime =
            new SimpleEntry<>("START_SLOT_TIME", "00:00");
    private static final SimpleEntry<String, String> endSlotTime =
            new SimpleEntry<>("END_SLOT_TIME", "24:00");

    private static final String CONFIG_PATH = "UDCalculator.config";
    private static Config config = null;

    private final Properties properties;

    private Config() {
        properties = new Properties();
        properties.setProperty(dateTimePattern.getKey(), dateTimePattern.getValue());
        properties.setProperty(datePattern.getKey(), datePattern.getValue());
        properties.setProperty(timePattern.getKey(), timePattern.getValue());
        properties.setProperty(slotSize.getKey(), slotSize.getValue());
        properties.setProperty(startSlotTime.getKey(), startSlotTime.getValue());
        properties.setProperty(endSlotTime.getKey(), endSlotTime.getValue());

        try {
            properties.load(Files.newInputStream(Path.of(CONFIG_PATH)));
        } catch (IOException e0) {
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Default");
            } catch (IOException e1) {
                System.err.println("Error: could not store the config file! Using defaults.");
            }
        } finally {
            properties.forEach((k, v) -> System.out.println(k + " " + v));
            try {
                DateTimeFormatter.ofPattern(properties.getProperty(dateTimePattern.getKey()));
            } catch (IllegalArgumentException e) {
                properties.setProperty(dateTimePattern.getKey(), dateTimePattern.getValue());
            }

            try {
                DateTimeFormatter.ofPattern(properties.getProperty(datePattern.getKey()));
            } catch (IllegalArgumentException e) {
                properties.setProperty(datePattern.getKey(), datePattern.getValue());
            }

            try {
                DateTimeFormatter.ofPattern(properties.getProperty(timePattern.getKey()));
            } catch (IllegalArgumentException e) {
                properties.setProperty(timePattern.getKey(), timePattern.getValue());
            }

            try {
                if (Integer.valueOf(properties.getProperty(slotSize.getKey())) < 1) {
                    properties.setProperty(slotSize.getKey(), slotSize.getValue());
                }
            } catch (NumberFormatException e) {
                properties.setProperty(slotSize.getKey(), slotSize.getValue());
            }

            // TODO missing slottime
        }
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
