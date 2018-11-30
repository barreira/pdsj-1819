package model;

import java.time.LocalTime;

public class BusySlot extends Slot {

    private final Task task;

    BusySlot(final int id, final LocalTime startTime, final LocalTime endTime, final Task task) {
        super(id, startTime, endTime);
        this.task = task;
    }

    BusySlot(BusySlot busySlot) {
        super(busySlot);
        this.task = busySlot.getTask();
    }

    public Task getTask() {
        return task.clone();
    }

    @Override
    public String toString() {
        return "BusySlot{" + "task=" + task + "} " + super.toString();
    }

    @Override
    public BusySlot clone() {
        return new BusySlot(this);
    }
}
