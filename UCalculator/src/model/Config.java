package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;

/**
 * Classe respons�vel por persistir e gerir configura��es assim como a estrutura do hor�rio.
 * Esta classe faz uso do padr�o de desenho Singleton.
 */
class Config {
    private static final String dateTimePattern = "dd-MM-yyyy HH:mm";
    private static final String datePattern = "dd-MM-yyyy";
    private static final String timePattern = "HH:mm";
    private static final String slotSize = "60";
    private static final String startSlotTime = "08:00";
    private static final String endSlotTime = "18:00";
    private static final String CONFIG_PATH = "UCalculator.config";
    private static volatile Config config = null;
    private final Properties properties;

    /**
     * Construtor privado.
     * N�o permite instancia��o por reflex�o.
     */
    private Config() {
        if (config != null){
            throw new RuntimeException("Singleton: use getInstance() method to retrieve the instance of this class.");
        }

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

    /**
     * M�todo privado respons�vel por validar a data e hora do padr�o definido pelo utilizador.
     *
     * @param pattern Padr�o definido pelo utilizador.
     * @return Se v�lido devolve true sen�o devolve false.
     */
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

    /**
     * M�todo privado respons�vel por validar a data do padr�o definido pelo utilizador.
     *
     * @param pattern Padr�o definido pelo utilizador.
     * @return Se v�lido devolve true sen�o devolve false.
     */
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

    /**
     * M�todo privado respons�vel por validar a hora do padr�o definido pelo utilizador.
     *
     * @param pattern Padr�o definido pelo utilizador.
     * @return Se v�lido devolve true sen�o devolve false.
     */
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

    /**
     * M�todo privado respons�vel por validar a dura��o do slot em minutos definido pelo utilizador.
     *
     * @param pattern Padr�o definido pelo utilizador.
     * @return Se v�lido devolve true sen�o devolve false.
     */
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

    /**
     * M�todo privado respons�vel por validar a hora de in�cio e fim do hor�rio definidas pelo utilizador.
     *
     * @param startSlotTime Hora de in�cio do hor�rio.
     * @param endSlotTime   Hora de fim do hor�rio.
     * @return Se v�lido devolve true sen�o devolve false.
     */
    private boolean isValidSlotsTime(String startSlotTime, String endSlotTime) {
        boolean isValid;
        try {
            isValid = LocalTime.parse(startSlotTime, DateTimeFormatter.ofPattern("HH:mm")).isBefore(LocalTime.parse(endSlotTime, DateTimeFormatter.ofPattern("HH:mm")));
        } catch (DateTimeParseException e) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * @return Devolve a �nica inst�ncia de Config.
     */
    static Config getInstance() {
        if (config == null) {
            // Avoid overhead of method synchronized
            synchronized (Config.class) {
                config = new Config();
            }
        }
        return config;
    }

    String getDateTimePattern() {
        return properties.getProperty("DATE_TIME_PATTERN");
    }

    String getDatePattern() {
        return properties.getProperty("DATE_PATTERN");
    }

    String getTimePattern() {
        return properties.getProperty("TIME_PATTERN");
    }

    private Integer getSlotSize() {
        return Integer.parseInt(properties.getProperty("SLOT_SIZE"));
    }

    private LocalTime getStartSlotTime() {
        return LocalTime.parse(properties.getProperty("START_SLOT_TIME"), DateTimeFormatter.ofPattern("HH:mm"));
    }

    private LocalTime getEndSlotTime() {
        return LocalTime.parse(properties.getProperty("END_SLOT_TIME"), DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * M�todo respons�vel por ler o estado anterior persistido da Schedule.
     *
     * @return Devolve uma inst�ncia de Schedule.
     */
    Schedule readSchedule() {
        Schedule schedule;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Path.of("schedule")))) {
            schedule = (Schedule) ois.readObject();
            if (schedule.getSlotSize() != config.getSlotSize()) {
                schedule = new Schedule(config.getSlotSize());
            }
            schedule.setStartSlot(config.getStartSlotTime());
            schedule.setEndSlot(config.getEndSlotTime());
        } catch (IOException | ClassNotFoundException e) {
            schedule = new Schedule(config.getSlotSize(), config.getStartSlotTime(), config.getEndSlotTime());
        }
        return schedule;
    }

    /**
     * M�todo respons�vel por persistir uma inst�ncia de Schedule.
     *
     * @param schedule O hor�rio a persistir.
     * @return Se foi poss�vel persistir devolve true sen�o devolve false.
     */
    boolean writeSchedule(Schedule schedule) {
        boolean success = true;
        try (ObjectOutputStream ous = new ObjectOutputStream(Files.newOutputStream(Path.of("schedule")))) {
            ous.writeObject(schedule);
        } catch (IOException e) {
            success = false;
        }
        return success;
    }
}
