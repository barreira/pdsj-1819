package model.schedule;

import java.time.LocalTime;

public class ClosedSlot extends Slot {
    public ClosedSlot(long id, LocalTime startTime, LocalTime endTime) {
        super(id, startTime, endTime);
    }

    public ClosedSlot(ClosedSlot closedSlot) {
        super(closedSlot);
    }

    @Override
    public String toString() {
        return "ClosedSlot{} " + super.toString();
    }

    @Override
    public ClosedSlot clone() {
        return new ClosedSlot(this);
    }
}
