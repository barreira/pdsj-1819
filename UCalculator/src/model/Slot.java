package model;

import java.time.LocalTime;
// available -
// unavailable -
//

// Map<Slot, Map<LocalDate, Properties>> slots
// slots.get(id)

class Slot {
    private final long id;
    private final LocalTime start;
    private final LocalTime end;

    Slot(long id, LocalTime start, LocalTime end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    long getId() {
        return id;
    }

    LocalTime getStart() {
        return start;
    }

    LocalTime getEnd() {
        return end;
    }


}
