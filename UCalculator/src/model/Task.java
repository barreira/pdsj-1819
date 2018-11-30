package model;

import java.time.LocalDate;
import java.util.List;

public class Task {

    private final LocalDate date;
    private final int slotId;
    private final int duration;
    private String title;
    private List<String> people;

    Task(LocalDate date, int slotId, int duration, String title, List<String> people) {
        this.date = date;
        this.slotId = slotId;
        this.duration = duration;
        this.title = title;
        this.people = people;
    }

    LocalDate getDate() {
        return date;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Task{" +
                ", date=" + date +
                ", slotId=" + slotId +
                ", duration=" + duration +
                ", title='" + title + '\'' +
                ", people=" + people +
                '}';
    }
}
