package model.schedule;

import java.time.LocalTime;

public class OpenSlot extends Slot{
    public OpenSlot(long id, LocalTime startTime, LocalTime endTime) {
        super(id, startTime, endTime);
    }

    @Override
    public String toString() {
        return "OpenSlot{} " + super.toString();
    }
}
