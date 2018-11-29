package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Schedule {
    private static final int DEFAULT_SLOT_SIZE = 30;
    private final int slotSize;
    private final List<SimpleEntry<LocalTime, LocalTime>> slots;
    private int startSlot;
    private int endSlot;
    private final List<Map<LocalDate, SimpleEntry<Boolean, Description>>> schedule;

    public Schedule(final int slotSize) {
        this.slotSize = 1440 % slotSize == 0 ? slotSize : DEFAULT_SLOT_SIZE;
        this.slots = new ArrayList<>();
        this.schedule = new ArrayList<>();

        for (int i = 0; i < 1440 / this.slotSize; i++) {
            this.slots.add(new SimpleEntry<>(LocalTime.of(0, 0).plusMinutes(i * this.slotSize),
                    LocalTime.of(0, 0).plusMinutes((i + 1) * this.slotSize)));
            this.schedule.add(new HashMap<>());
        }

        this.startSlot = 1;
        this.endSlot = slots.size();
    }

    Schedule() {
        this(DEFAULT_SLOT_SIZE);
    }

    public int getSlotSize() {
        return slotSize;
    }

    public int getStartSlot() {
        return startSlot;
    }

    public int getEndSlot() {
        return endSlot;
    }

    public int setStartSlot(int startSlot) {
        this.startSlot = startSlot < 1 || startSlot > endSlot ? this.startSlot : startSlot;
        return this.startSlot;
    }

    public int setEndSlot(int endSlot) {
        this.endSlot = endSlot < startSlot || endSlot > slots.size() ? this.endSlot : endSlot;
        return this.endSlot;
    }

    public boolean fillSlot(int slotId, LocalDate date, String description, List<String> people, int duration) {
        if (slotId < 1 || startSlot + slotId - 1 > endSlot || duration < 1) {
            return false;
        }

        LocalDate aux = date;
        for (int i = 0; i < duration; i++) {
            for (int j = startSlot + slotId - 2; j < endSlot && i < duration; j++, i++) {
                if (schedule.get(j).get(date) != null) {
                    return false;
                }
            }
            aux = aux.plusDays(1);
        }

        for (int i = 0; i < duration; i++) {
            for (int j = startSlot + slotId - 2; j < endSlot && i < duration; j++, i++) {
                schedule.get(j).put(date, new SimpleEntry<>(true, new Description(description, people, i == duration - 1)));
            }
            date = date.plusDays(1);
        }

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i) != null) {
                System.out.println("Slot " + (i + 1) + " " + slots.get(i).getKey() + " " + slots.get(i).getValue());
                schedule.get(i).forEach((k, v) -> System.out.println(k + " " + v.getValue().toString()));
            }
        }

        return true;
    }

    public boolean fillSlot(int slotId, LocalDate date, String description, List<String> people) {
        return fillSlot(slotId, date, description, people, 1);
    }


}





