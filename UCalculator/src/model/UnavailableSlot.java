package model;

import java.time.LocalTime;

public class UnavailableSlot extends Slot {
    UnavailableSlot(long id, LocalTime start, LocalTime end) {
        super(id, start, end);
    }
}
