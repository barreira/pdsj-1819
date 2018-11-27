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
            slots.add(new Slot(idCounter, description, start, end, true));
        } else {
            slots.add(new Slot(idCounter, description, start, start.toLocalDate().atTime(23, 59), false));

            for (int i = 1; i < days; i++) {
                slots.add(new Slot(idCounter, description,
                        start.plusDays(i).toLocalDate().atTime(0, 0),
                        start.plusDays(i).toLocalDate().atTime(23, 59), false));
            }

            slots.add(new Slot(idCounter, description, start.plusDays(days).toLocalDate().atTime(0, 0), end, true));
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

        this.idCounter++;
        //schedule.values().forEach(m -> m.forEach((k, v) -> System.out.println(k + " " + v.toString())));
        return true;
    }

    public void remove(long id) {
        if (this.idCounter > id) {
            schedule.values().forEach(v -> v.remove(id));
        }
        schedule.values().forEach(m -> m.forEach((k, v) -> System.out.println(k + " " + v.toString())));
    }

    public List<Slot> consult(LocalDate date) {
        Map<Long, Slot> value = schedule.get(date);
        List<Slot> slots = new ArrayList<>();
        if (value != null) {
            slots = value.values().stream().map(Slot::clone).collect(Collectors.toList());
            slots.sort(Comparator.comparing(Slot::getStart, LocalDateTime::compareTo));
        }
        return slots;
    }

    public List<Slot> consult(long id) {
        return schedule.values()
                .stream()
                .map(slots -> slots.get(id))
                .filter(Objects::nonNull)
                .map(Slot::clone)
                .collect(Collectors.toList());
    }

    public void edit(long id, String description) {
        schedule.values()
                .stream()
                .map(slots -> slots.get(id))
                .filter(Objects::nonNull)
                .forEach(s -> s.setDescription(description));
    }

    public boolean edit(long id, LocalDateTime start, LocalDateTime end) {
        List<Slot> slots = this.consult(id);
        if (slots != null) {
            String description = slots.get(0).getDescription();
            this.remove(id);
            return this.add(description, start, end);
        } else {
            return false;
        }
    }
}





