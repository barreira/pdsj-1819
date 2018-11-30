package model.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final String CONFIG_PATH = "UDCalculator.config";
    private static Config config = null;

    private Config() {
        Properties config = new Properties();
        config.setProperty("DATE_TIME_PATTERN", "dd/MM/yyyy HH:mm");
        config.setProperty("DATE_PATTERN", "dd/MM/yyyy");
        config.setProperty("TIME_PATTERN", "HH:mm");
        config.setProperty("SLOT_SIZE", "15");
        config.setProperty("START_SLOT_TIME", "00:00");
        config.setProperty("END_SLOT_TIME", "23:45");

        try {
            config.load(Files.newInputStream(Path.of(CONFIG_PATH)));
            config.forEach((k, v) -> System.out.println(k + " " + v));
        } catch (IOException e0) {
            try {
                config.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Default");
            } catch (IOException e1) {
                System.err.println("An error occurred: could not store the config file!");
            }
        } finally {
            // TODO complete verifications
            try {
                Integer.valueOf(config.getProperty("SLOT_SIZE"));
            } catch (NumberFormatException e) {
                config.setProperty("SLOT_SIZE", "15");
            }
        }
    }

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public String getProperty(final String key) {
        return config.getProperty(key);
    }
}
