package model;

import java.time.LocalTime;

public class ClosedSlot extends Slot {

    ClosedSlot(int id, LocalTime startTime, LocalTime endTime) {
        super(id, startTime, endTime);
    }

    ClosedSlot(ClosedSlot closedSlot) {
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
