package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Classe abstrata que representa um slot.
 * Um slot é representado por um id, uma hora de início e uma hora de fim.
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
public abstract class Slot implements Serializable {

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

    private int getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
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

    // Serialize
    private Slot() {
        id = 0;
        startTime = null;
        endTime = null;
    }
}
