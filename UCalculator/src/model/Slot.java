package model;

import java.time.LocalTime;

public abstract class Slot {

    private final int id;
    private final LocalTime startTime;
    private final LocalTime endTime;

    Slot(final int id, final LocalTime startTime, final LocalTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    Slot(Slot slot) {
        this.id = slot.getId();
        this.startTime = slot.getStartTime();
        this.endTime = slot.getEndTime();
    }

    int getId() {
        return id;
    }

    LocalTime getStartTime() {
        return startTime;
    }

    LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public abstract Slot clone();
}
