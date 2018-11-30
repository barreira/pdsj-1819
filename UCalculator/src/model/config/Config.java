package model.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final String CONFIG_PATH = "UDCalculator.config";
    private static Config config = null;
    private final Properties properties;

    private Config() {
        properties = new Properties();
        properties.setProperty("DATE_TIME_PATTERN", "dd/MM/yyyy HH:mm");
        properties.setProperty("DATE_PATTERN", "dd/MM/yyyy");
        properties.setProperty("TIME_PATTERN", "HH:mm");
        properties.setProperty("SLOT_SIZE", "15");
        properties.setProperty("START_SLOT_TIME", "00:00");
        properties.setProperty("END_SLOT_TIME", "23:45");

        try {
            properties.load(Files.newInputStream(Path.of(CONFIG_PATH)));
            // properties.forEach((k, v) -> System.out.println(k + " " + v));
        } catch (IOException e0) {
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Default");
            } catch (IOException e1) {
                System.err.println("An error occurred: could not store the config file!");
            }
        } finally {
            // TODO complete verifications
            try {
                Integer.valueOf(properties.getProperty("SLOT_SIZE"));
            } catch (NumberFormatException e) {
                properties.setProperty("SLOT_SIZE", "15");
            }
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
