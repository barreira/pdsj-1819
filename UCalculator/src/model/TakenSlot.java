package model;

import java.time.LocalTime;
import java.util.List;

public class TakenSlot extends Slot {
    private boolean last;
    private String title;
    private List<String> people;

    TakenSlot(long id, LocalTime start, LocalTime end, boolean last, String title, List<String> people) {
        super(id, start, end);
        this.title = title;
        this.last = last;
        this.people = people;
    }

    TakenSlot(TakenSlot takenSlot) {
        super(takenSlot.getId(), takenSlot.getStart(), takenSlot.getEnd());
        this.last = takenSlot.isLast();
        this.title = takenSlot.getTitle();
        this.people = takenSlot.getPeople();
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

    // TODO: missing parameters on return
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if(o == null || this.getClass() != o.getClass())  {
            return false;
        }

        return super.equals(o);
    }

    // TODO: missing toString
    @Override
    public String toString() {
        // String result = "Description: " + title + "\n";
        // result += "Start: " + start + "\n";
        // result += "End: " + end + "\n";
        // return result;

        return super.toString();
    }

    @Override
    public TakenSlot clone() {
        return new TakenSlot(this);
    }
}
