package model;

import java.time.LocalTime;

public abstract class Slot {
    private final long id;
    private final LocalTime start;
    private final LocalTime end;

    public Slot(long id, LocalTime start, LocalTime end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
