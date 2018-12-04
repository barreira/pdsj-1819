package model;

import java.io.Serializable;
import java.time.LocalTime;

public class OpenSlot extends Slot implements Serializable {

    OpenSlot(int id, LocalTime startTime, LocalTime endTime) {
        super(id, startTime, endTime);
    }

    OpenSlot(OpenSlot openSlot) {
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
