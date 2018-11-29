package model;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Description {
    private String title;
    private List<String> people;
    private boolean last;

    Description(String title, List<String> people, boolean last) {
        this.title = title;
        this.last = last;
        this.people = people;
    }

    Description(Description description) {
        this.last = description.isLast();
        this.title = description.getTitle();
        this.people = description.getPeople();
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
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

        Description that = (Description) o;

        return isLast() == that.isLast() &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getPeople(), that.getPeople());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, people, last);
    }

    @Override
    public String toString() {
        return "Description{" +
                "title='" + title + '\'' +
                ", people=" + people +
                ", last=" + last +
                '}';
    }

    @Override
    public Description clone() {
        return new Description(this);
    }
}
