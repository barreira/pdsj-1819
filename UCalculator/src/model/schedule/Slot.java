package model.schedule;

import java.time.LocalTime;

public abstract class Slot {
    private final long id;
    private final LocalTime startTime;
    private final LocalTime endTime;

    Slot(final long id, final LocalTime startTime, final LocalTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    long getId() {
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
}
