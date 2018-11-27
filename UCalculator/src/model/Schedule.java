package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Schedule {
    private long idCounter;
    private Map<LocalDate, Map<Long, Slot>> schedule;

    public Schedule() {
        idCounter = 1;
        schedule = new HashMap<>();
    }

    public boolean add(String description, LocalDateTime start, LocalDateTime end) {
        if (!start.isBefore(end)) {
            return false;
        }

        long days = ChronoUnit.DAYS.between(start, end);
        List<Slot> slots = new ArrayList<>();

        if (days == 0) {
            slots.add(new Slot(idCounter, description, start, end));
        } else {
            slots.add(new Slot(idCounter, description, start, start.toLocalDate().atTime(23, 59)));

            for (int i = 1; i < days; i++) {
                slots.add(new Slot(idCounter, description,
                        start.plusDays(i).toLocalDate().atTime(0, 0),
                        start.plusDays(i).toLocalDate().atTime(23, 59)));
            }

            slots.add(new Slot(idCounter, description, start.plusDays(days).toLocalDate().atTime(0, 0), end));
        }

        slots.forEach(s -> {
            if (!schedule.containsKey(s.getStart().toLocalDate())) {
                Map<Long, Slot> value = new HashMap<>();
                value.put(s.getId(), s);
                schedule.put(s.getStart().toLocalDate(), value);
            } else {
                Map<Long, Slot> value = schedule.get(s.getStart().toLocalDate());
                value.put(s.getId(), s);
            }
        });

        this.idCounter++;

        schedule.values().forEach(m -> m.forEach((k, v) -> System.out.println(k + " " + v.toString())));
        return true;
    }
}





