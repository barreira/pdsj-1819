package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Schedule {
    private long idCounter;
    private Map<LocalDate, Map<Long, Slot>> schedule;

    Schedule() {
        idCounter = 1;
        schedule = new HashMap<>();
    }

    boolean add(String description, LocalDateTime start, LocalDateTime end) {
        if (add(idCounter, description, start, end)) {
            idCounter++;
            return true;
        }
        return false;
    }

    List<Slot> remove(long id) {
        List<Slot> slots = new ArrayList<>();
        if (this.idCounter > id) {
            schedule.values().forEach(v -> slots.add(v.remove(id)));
        }
        return slots;
    }

    List<Slot> consult(LocalDate date) {
        Map<Long, Slot> value = schedule.get(date);
        List<Slot> slots = new ArrayList<>();
        if (value != null) {
            slots = value.values().stream().map(Slot::clone).collect(Collectors.toList());
            slots.sort(Comparator.comparing(Slot::getStart, LocalDateTime::compareTo));
        }
        return slots;
    }

    List<Slot> consult(long id) {
        return schedule.values()
                .stream()
                .map(slots -> slots.get(id))
                .filter(Objects::nonNull)
                .map(Slot::clone)
                .collect(Collectors.toList());
    }

    void edit(long id, String description) {
        schedule.values()
                .stream()
                .map(slots -> slots.get(id))
                .filter(Objects::nonNull)
                .forEach(s -> s.setDescription(description));
    }

    public boolean edit(long id, LocalDateTime start, LocalDateTime end) {
        List<Slot> slots = this.remove(id);
        boolean success = false;
        if (slots != null) {
            if (!this.add(id, slots.get(0).getDescription(), start, end)) {
                this.add(id, slots.get(0).getDescription(), slots.get(0).getStart(), slots.get(slots.size() - 1).getEnd());
            } else {
                success = true;
            }
        }
        return success;
    }

    private boolean add(long id, String description, LocalDateTime start, LocalDateTime end) {
        if (!start.isBefore(end)) {
            return false;
        }

        long days = ChronoUnit.DAYS.between(start, end);
        List<Slot> slots = new ArrayList<>();

        if (days == 0) {
            slots.add(new Slot(id, description, start, end, true));
        } else {
            slots.add(new Slot(id, description, start, start.toLocalDate().atTime(23, 59), false));

            for (int i = 1; i < days; i++) {
                slots.add(new Slot(id, description,
                        start.plusDays(i).toLocalDate().atTime(0, 0),
                        start.plusDays(i).toLocalDate().atTime(23, 59), false));
            }

            slots.add(new Slot(id, description, start.plusDays(days).toLocalDate().atTime(0, 0), end, true));
        }

        for (Slot s : slots) {
            Map<Long, Slot> value = schedule.get(s.getStart().toLocalDate());
            if (value != null) {
                if(value.values().stream().anyMatch(v -> DateUtils.intersects(s.getStart(), s.getEnd(), v.getStart(), v.getEnd()))) {
                    return false;
                }
            }
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

        return true;
    }
}




