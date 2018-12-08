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
 * Classe responsável por persistir e gerir configurações assim como a estrutura da agenda.
 * Esta classe faz uso do padrão de desenho Singleton.
 */
class Config {
    private static final String dateTimePattern = "dd-MM-yyyy HH:mm";
    private static final String datePattern = "dd-MM-yyyy";
    private static final String timePattern = "HH:mm";
    private static final String slotSize = "60";
    private static final String startTime = "08:00";
    private static final String endTime = "18:00";
    private static final String CONFIG_PATH = "UCalculator.config";
    private static volatile Config config = null;
    private final Properties properties;

    /**
     * Construtor privado.
     * Não permite instanciaçãoo por reflexão.
     */
    private Config() {
        if (config != null){
            throw new RuntimeException("Singleton: use getInstance() method to retrieve the instance of this class.");
        }

        properties = new Properties(6);
        setDefault(false);

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

            if (!isValidSlotsTime(properties.getProperty("START_TIME"), properties.getProperty("END_TIME"))) {
                properties.setProperty("START_TIME", startTime);
                properties.setProperty("END_TIME", endTime);
            }
        } catch (IOException e) {
            setDefault();
        }

        try {
            properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Custom");
        } catch (IOException ignored) {
        }
    }

    /**
     * Método privado responsável por validar a data e hora do padrão definido pelo utilizador.
     *
     * @param pattern Padrão definido pelo utilizador.
     * @return Se válido devolve true senão devolve false.
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
     * Método privado responsável por validar a data do padrão definido pelo utilizador.
     *
     * @param pattern Padrão definido pelo utilizador.
     * @return Se válido devolve true senão devolve false.
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
     * Método privado responsável por validar a hora do padrão definido pelo utilizador.
     *
     * @param pattern Padrão definido pelo utilizador.
     * @return Se válido devolve true senão devolve false.
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
     * Método privado responsável por validar a duração do slot em minutos definido pelo utilizador.
     *
     * @param pattern Padrão definido pelo utilizador.
     * @return Se válido devolve true senão devolve false.
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
     * Método privado responsável por validar a hora de início e fim do horário definidas pelo utilizador.
     *
     * @param startSlotTime Hora de início do horário.
     * @param endSlotTime   Hora de fim do horário.
     * @return Se válido devolve true senão devolve false.
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
     * @return Devolve a única instância de Config.
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
        return LocalTime.parse(properties.getProperty("START_TIME"), DateTimeFormatter.ofPattern("HH:mm"));
    }

    private LocalTime getEndSlotTime() {
        return LocalTime.parse(properties.getProperty("END_TIME"), DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Método responsável por ler o estado anterior persistido da Schedule.
     *
     * @return Devolve uma instância de Schedule.
     */
    Schedule readSchedule() {
        Schedule schedule;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Path.of("schedule")))) {
            schedule = (Schedule) ois.readObject();
            if (schedule.getSlotSize() != config.getSlotSize()) {
                schedule = new Schedule(config.getSlotSize());
            }
            schedule.setStartTime(config.getStartSlotTime());
            schedule.setEndTime(config.getEndSlotTime());
        } catch (IOException | ClassNotFoundException e) {
            schedule = new Schedule(config.getSlotSize(), config.getStartSlotTime(), config.getEndSlotTime());
        }
        return schedule;
    }

    /**
     * Método responsável por persistir uma instância de Schedule.
     *
     * @param schedule O horário a persistir.
     * @return Se foi possível persistir devolve true senão devolve false.
     */
    boolean storeSchedule(Schedule schedule) {
        boolean success = true;
        try (ObjectOutputStream ous = new ObjectOutputStream(Files.newOutputStream(Path.of("schedule")))) {
            ous.writeObject(schedule);
        } catch (IOException e) {
            success = false;
        }
        return success;
    }

    boolean setDateTimePattern(String pattern) {
        boolean success = false;
        if (isValidDateTimePattern(pattern)) {
            success = true;
            properties.setProperty("DATE_TIME_PATTERN", pattern);
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Custom");
            } catch (IOException e) {
                success = false;
            }
        }

        return success;
    }

    boolean setDatePattern(String pattern) {
        boolean success = false;
        if (isValidDatePattern(pattern)) {
            success = true;
            properties.setProperty("DATE_PATTERN", pattern);
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Custom");
            } catch (IOException e) {
                success = false;
            }
        }

        return success;
    }

    boolean setTimePattern(String pattern) {
        boolean success = false;
        if (isValidTimePattern(pattern)) {
            success = true;
            properties.setProperty("TIME_PATTERN", pattern);
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Custom");
            } catch (IOException e) {
                success = false;
            }
        }

        return success;
    }

    boolean setSlotSize(int slotSize) {
        boolean success = false;
        if (slotSize > 0 && slotSize < 1441) {
            success = true;
            properties.setProperty("SLOT_SIZE", String.valueOf(slotSize));
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Custom");
            } catch (IOException e) {
                success = false;
            }
        }
        return success;
    }

    boolean setStartTime(LocalTime time) {
        boolean success = false;
        if (time.isBefore(LocalTime.parse(properties.getProperty("END_TIME")))) {
            success = true;
            properties.setProperty("START_TIME", time.toString());
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Custom");
            } catch (IOException e) {
                success = false;
            }
        }
        return success;
    }

    boolean setEndTime(LocalTime time) {
        boolean success = false;
        if (time.isAfter(LocalTime.parse(properties.getProperty("START_TIME")))) {
            success = true;
            properties.setProperty("END_TIME", time.toString());
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Custom");
            } catch (IOException e) {
                success = false;
            }
        }
        return success;
    }

    void setDefault() {
        setDefault(true);
    }

    /**
     * Método privado responsável por resetar as configurações para os valores por defeito.
     *
     * @param store Indica se é prentendido persistir as alterações.
     */
    private void setDefault(boolean store) {
        properties.setProperty("DATE_TIME_PATTERN", dateTimePattern);
        properties.setProperty("DATE_PATTERN", datePattern);
        properties.setProperty("TIME_PATTERN", timePattern);
        properties.setProperty("SLOT_SIZE", slotSize);
        properties.setProperty("START_TIME", startTime);
        properties.setProperty("END_TIME", endTime);

        if (store) {
            try {
                properties.store(Files.newOutputStream(Path.of(CONFIG_PATH)), "Default");
            } catch (IOException ignored) {
            }
        }
    }
}
