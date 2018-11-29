package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Schedule {
    private static final int DEFAULT_SLOT_SIZE = 30;
    private final int slotSize;
    private final List<SimpleEntry<LocalTime, LocalTime>> slots;
    private int startSlot;
    private int endSlot;
    private int taskId;
    private final List<Map<LocalDate, SimpleEntry<Boolean, Task>>> schedule;
    private final Map<Integer, SimpleEntry<Integer, LocalDate>> task_slot_date;

    public Schedule(final int slotSize) {
        this.slotSize = 1440 % slotSize == 0 ? slotSize : DEFAULT_SLOT_SIZE;
        taskId = 1;
        this.slots = new ArrayList<>();
        this.schedule = new ArrayList<>();

        for (int i = 0; i < 1440 / this.slotSize; i++) {
            this.slots.add(new SimpleEntry<>(LocalTime.of(0, 0).plusMinutes(i * this.slotSize),
                    LocalTime.of(0, 0).plusMinutes((i + 1) * this.slotSize)));
            this.schedule.add(new HashMap<>());
        }

        this.startSlot = 1;
        this.endSlot = slots.size();

        task_slot_date = new HashMap<>();
    }

    Schedule() {
        this(DEFAULT_SLOT_SIZE);
    }

    public int getSlotSize() {
        return slotSize;
    }

    public int getStartSlot() {
        return startSlot;
    }

    public int getEndSlot() {
        return endSlot;
    }

    public int setStartSlot(int startSlot) {
        this.startSlot = startSlot < 1 || startSlot > endSlot ? this.startSlot : startSlot;
        return this.startSlot;
    }

    public int setEndSlot(int endSlot) {
        this.endSlot = endSlot < startSlot || endSlot > slots.size() ? this.endSlot : endSlot;
        return this.endSlot;
    }

    public boolean fillSlot(final int slotId, final LocalDate date, final String description, final List<String> people, final int duration) {
        if (slotId < 1 || startSlot + slotId - 1 > endSlot || duration < 1) {
            return false;
        }

        LocalDate aux = date;

        int k = startSlot + slotId - 2;
        for (int i = 0; i < duration; i++) {

            for (int j = k; j < endSlot && i < duration; j++, i++) {
                if (schedule.get(j).get(date) != null) {
                    return false;
                }
            }

            i--;
            k = 0;
            aux = aux.plusDays(1);
        }

        aux = date;
        k = startSlot + slotId - 2;
        for (int i = 0; i < duration; i++) {

            for (int j = k; j < endSlot && i < duration; j++, i++) {
                schedule.get(j).put(aux, new SimpleEntry<>(true, new Task(taskId, j, aux, description, people, i == duration - 1)));
            }

            i--;
            k = 0;
            aux = aux.plusDays(1);
        }

        task_slot_date.put(taskId, new SimpleEntry<>(slotId, date));

        taskId++;

        for (int i = 0; i < schedule.size(); i++) {

            if (schedule.get(i) != null) {
                System.out.println("Slot " + (i + 1) + " " + slots.get(i).getKey() + " " + slots.get(i).getValue());
                schedule.get(i).forEach((x, v) -> System.out.println(x + " " + v.getValue().toString()));
            }
        }

        return true;
    }

    public boolean fillSlot(final int slotId, final LocalDate date, final String description, final List<String> people) {
        return fillSlot(slotId, date, description, people, 1);
    }

    public List<Task> remove(final int slotId, final LocalDate date) {
        final List<Task> removed = new ArrayList<>();

        SimpleEntry<Boolean, Task> task = schedule.get(startSlot + slotId - 2).get(date);

        if (task == null || !task.getKey()) {
            return removed;
        }

        int taskId = task.getValue().getTaskId();
        boolean exit = false;
        LocalDate aux = task_slot_date.get(taskId).getValue();

        int j = task_slot_date.get(taskId).getKey() + startSlot - 2;

        while (!exit) {
            for (int i = j; i < endSlot && !exit;  i++) {
                SimpleEntry<Boolean, Task> it = schedule.get(i).get(aux);
                if (it == null || taskId != it.getValue().getTaskId()) {
                    exit = true;
                } else {
                    removed.add(schedule.get(i).remove(aux).getValue());
                }
            }
            aux = aux.plusDays(1);
            j = startSlot - 1;
        }
        task_slot_date.remove(taskId);
        removed.forEach(System.out::println);
        return removed;
    }

    public boolean edit(final int old_slotId, final LocalDate old_date, final int new_slotId, final LocalDate new_date) {
        List<Task> tasks = this.remove(old_slotId, old_date);
        this.fillSlot(new_slotId, new_date, tasks.get(0).getTitle(), tasks.get(0).getPeople(), tasks.size());
        return true;
    }
}





