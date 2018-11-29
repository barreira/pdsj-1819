package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Task {
    private final int taskId;
    private final int slotId;
    private final LocalDate date;
    private final boolean last;

    private String title;
    private List<String> people;

    Task(int taskId, int slotId, LocalDate date, String title, List<String> people, boolean last) {
        this.taskId = taskId;
        this.slotId = slotId;
        this.date = date;
        this.title = title;
        this.last = last;
        this.people = people;
    }

    Task(Task task) {
        this.taskId = task.getTaskId();
        this.slotId = task.getSlotId();
        this.date = task.getDate();
        this.last = task.isLast();
        this.title = task.getTitle();
        this.people = task.getPeople();
    }

    public int getTaskId() {
        return taskId;
    }

    public int getSlotId() {
        return slotId;
    }


    public LocalDate getDate() {
        return date;
    }

    public boolean isLast() {
        return last;
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
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || this.getClass() != o.getClass()) return false;

        Task that = (Task) o;

        return isLast() == that.isLast() &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getPeople(), that.getPeople());
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotId, date, title, people, last);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", slotId=" + slotId +
                ", date=" + date +
                ", last=" + last +
                ", title='" + title + '\'' +
                ", people=" + people +
                '}';
    }

    @Override
    public Task clone() {
        return new Task(this);
    }
}
