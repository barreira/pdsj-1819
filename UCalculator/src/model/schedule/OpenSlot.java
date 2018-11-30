package model.schedule;

import java.time.LocalTime;

public class OpenSlot extends Slot{
    public OpenSlot(long id, LocalTime startTime, LocalTime endTime) {
        super(id, startTime, endTime);
    }

    public OpenSlot(OpenSlot openSlot) {
        super(openSlot);
    }

    @Override
    public String toString() {
        return "OpenSlot{} " + super.toString();
    }

    @Override
    public OpenSlot clone() {
        return new OpenSlot(this);
    }
}
