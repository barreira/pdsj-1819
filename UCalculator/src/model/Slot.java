package model;

import java.time.LocalDateTime;

public class Slot {
    private final long id;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;

    public Slot(long id, String description, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if(o == null || this.getClass() != o.getClass())  {
            return false;
        }

        return this.id == ((Slot) o).getId();
    }

    @Override
    public String toString() {
        String result = "Description: " + description + "\n";
        result += "Start: " + start + "\n";
        result += "End: " + end + "\n";
        return result;
    }
}
