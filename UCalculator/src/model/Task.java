package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task implements Serializable {

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

    Task(final Task task) {
        this.date = task.getDate();
        this.title = task.getTitle();
        this.duration = task.getDuration();
        this.slotId = task.getSlotId();
        this.title = task.getTitle();
        this.people = task.getPeople();
    }

    LocalDate getDate() {
        return date;
    }

    int getSlotId() {
        return slotId;
    }

    int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPeople() {
        return new ArrayList<>(people);
    }

    void setPeople(List<String> people) {
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

    @Override
    public Task clone() {
        return new Task(this);
    }

    // Serialize
    private Task() {
        date = null;
        slotId = 0;
        duration = 0;
    }

}
