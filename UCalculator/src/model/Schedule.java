package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Schedule {
    private static final int DEFAULT_SLOT_SIZE = 30;
    private final int slotSize;
    private int startSlot;
    private int endSlot;
    private final int totalSlots;
    private final Map<LocalDate, List<TakenSlot>> schedule;

    Schedule(final int slotSize) {
        this.slotSize = 1440 % slotSize == 0 ? slotSize : DEFAULT_SLOT_SIZE;
        this.schedule = new HashMap<>();
        this.startSlot = 1;
        this.totalSlots = 1440 / this.slotSize;
        this.endSlot = this.totalSlots;
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
        this.endSlot = endSlot < startSlot || endSlot > totalSlots ? this.endSlot : endSlot;
        return this.endSlot;
    }

    boolean fillSlot(int slotId, LocalDate date, String description, List<String> people, int numSlots) {
        if (slotId < 1 || startSlot - 1 + slotId > endSlot) {
            return false;
        }

        List<TakenSlot> slots = schedule.get(date);
        if (slots == null) {
            slots = new ArrayList<>(numSlots);
            LocalTime start = LocalTime.of(0, 0).plus(Duration.ofMinutes((startSlot + slotId - 1) * slotSize));
            LocalTime end = start.plus(Duration.ofMinutes(numSlots * slotSize));
            slots.add(new TakenSlot(slotId, start, end, true, description, people));


           schedule.put(date, slots);
        }
        return false;
    }

    boolean fillSlot(int slotId, LocalDate date, String description, List<String> people) {
        return fillSlot(slotId, date, description, people, 1);
    }


}





