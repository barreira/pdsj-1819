package model.schedule;

import java.time.LocalTime;

public class BusySlot extends Slot {
    private final Task task;

    public BusySlot(final long id, final LocalTime startTime, final LocalTime endTime, final Task task) {
        super(id, startTime, endTime);
        this.task = task;
    }

    public BusySlot(BusySlot busySlot) {
        super(busySlot);
        this.task = busySlot.getTask();
    }

    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "BusySlot{" +
                "task=" + task +
                "} " + super.toString();
    }

    @Override
    public BusySlot clone() {
        return new BusySlot(this);
    }
}
