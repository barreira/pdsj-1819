package model.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Schedule {
    private static final int DEFAULT_SLOT_SIZE = 30;
    private final int slotSize;
    private int startSlotId;
    private int endSlotId;
    private int taskId;
    private final Map<LocalDate, List<Slot>> schedule;

    public Schedule(final int slotSize) {
        this.slotSize = 1440 % slotSize == 0 ? slotSize : DEFAULT_SLOT_SIZE;
        /*
        for (int i = 0; i < 1440 / this.slotSize; i++) {
            this.slots.addTask(new Slot(i, LocalTime.of(0, 0).plusMinutes(i * this.slotSize), LocalTime.of(0, 0).plusMinutes((i + 1) * this.slotSize)));
        }*/

        // internal ids
        this.startSlotId = 0;
        this.endSlotId = 1440 / this.slotSize - 1;
        this.taskId = 1;
        this.schedule = new HashMap<>();
    }

    Schedule() {
        this(DEFAULT_SLOT_SIZE);
    }

    public int getSlotSize() {
        return slotSize;
    }

    public int getStartSlotId() {
        return startSlotId;
    }

    public int getEndSlotId() {
        return endSlotId;
    }

    public int setStartSlot(int startSlot) {
        this.startSlotId = startSlot < 1 || startSlot > endSlotId ? this.startSlotId : startSlot;
        return this.startSlotId;
    }

    public int setEndSlot(int endSlot) {
        this.endSlotId = endSlot < startSlotId || endSlot > 1440 / this.slotSize ? this.endSlotId : endSlot;
        return this.endSlotId;
    }

    public boolean addTask(final LocalDate date, final int slotId, final int duration, final String title, final List<String> people) {
        final int totalSlots = 1440 / this.slotSize;
        boolean success = true;

        if (slotId < 0 || startSlotId + slotId > endSlotId || duration < 1) {
            success = false;
        } else {
            Task task = new Task(taskId, date, slotId, duration, title, people);
            List<Slot> slots = schedule.get(date);

            if (slots == null) {
                slots = new ArrayList<>();

                for (int i = 0; i < totalSlots; i++) {
                    slots.add(new OpenSlot(i, this.slotTime(i), this.slotTime(i + 1)));
                }

                slots.set(slotId, new BusySlot(slotId, this.slotTime(slotId), this.slotTime(slotId + 1), task));
                this.schedule.put(date, slots);
                taskId++;
            } else if (slots.get(slotId).getClass().equals(OpenSlot.class)) {
                slots.set(slotId, new BusySlot(slotId, this.slotTime(slotId), this.slotTime(slotId + 1), task));
                taskId++;
            } else {
                success = false;
            }
        }

        schedule.get(date).forEach(System.out::println);

        return success;
    }

    /*
    public boolean addTask(final int slotId, final LocalDate date, final String description, final List<String> people, final int duration) {
        if (slotId < 1 || startSlotId + slotId - 1 > endSlotId || duration < 1) {
            return false;
        }

        LocalDate aux = date;

        int k = startSlotId + slotId - 2;
        for (int i = 0; i < duration; i++) {

            for (int j = k; j < endSlotId && i < duration; j++, i++) {
                if (schedule.get(j).get(date) != null) {
                    return false;
                }
            }

            i--;
            k = 0;
            aux = aux.plusDays(1);
        }

        aux = date;
        k = startSlotId + slotId - 2;
        for (int i = 0; i < duration; i++) {

            for (int j = k; j < endSlotId && i < duration; j++, i++) {
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
    */
    public boolean addTask(final LocalDate date, final int slotId, final String title, final List<String> people) {
        return addTask( date, slotId, 1, title, people);
    }

    public Task removeTask(final LocalDate date, final int slotId) {
        Task task = null;

        // TODO verify if slotId must is higher than startSlotId less or equal than endSlotId
        List<Slot> slots = schedule.get(date);
        Slot slot;
        if (slots != null && (slot = slots.get(slotId - 1)).getClass().equals(BusySlot.class)) {
            task = ((BusySlot) slot).getTask();
            slots.set(slotId - 1, new OpenSlot(slotId - 1, this.slotTime(slotId), this.slotTime(slotId + 1)));
        }
        return task;
    }

    public boolean editTask(final LocalDate date, final int slotId, final LocalDate newDate,
                            final int newSlotId, final int newDuration) {
        if (newDuration != 1) {
            System.err.println("NOT IMPLEMENTED YET: DURATION MUST BE 1");
            System.exit(1);
        }

        boolean success = true;
        Task task = this.removeTask(date, slotId);
        if (task == null) {
            success = false;
        } else if (!this.addTask(newDate, newSlotId, newDuration, task.getTitle(), task.getPeople())) {
            success = !this.addTask(task);
        }
        return success;
    }

    public List<Slot> consult(final LocalDate date) {
        return schedule.get(date);
    }

    /*
    public List<Task> remove(final LocalDate date, final int slotId) {
        final List<Task> removed = new ArrayList<>();

        SimpleEntry<Boolean, Task> task = schedule.get(startSlotId + slotId - 2).get(date);

        if (task == null || !task.getKey()) {
            return removed;
        }

        int taskId = task.getValue().getId();
        boolean exit = false;
        LocalDate aux = task_slot_date.get(taskId).getValue();

        int j = task_slot_date.get(taskId).getKey() + startSlotId - 2;

        while (!exit) {
            for (int i = j; i < endSlotId && !exit;  i++) {
                SimpleEntry<Boolean, Task> it = schedule.get(i).get(aux);
                if (it == null || taskId != it.getValue().getId()) {
                    exit = true;
                } else {
                    removed.addTask(schedule.get(i).remove(aux).getValue());
                }
            }
            aux = aux.plusDays(1);
            j = startSlotId - 1;
        }
        task_slot_date.remove(taskId);
        removed.forEach(System.out::println);
        return removed;
    }

    public boolean edit(final int old_slotId, final LocalDate old_date, final int new_slotId, final LocalDate new_date) {
        List<Task> tasks = this.remove(old_slotId, old_date);
        this.addTask(new_slotId, new_date, tasks.get(0).getTitle(), tasks.get(0).getPeople(), tasks.size());
        return true;
    }
    */
    private boolean addTask(Task task) {
        // TODO create a new addTask method that receives the previous taskId and verify if taskId is needed
        return this.addTask(task.getDate(), task.getSlotId(), task.getDuration(), task.getTitle(), task.getPeople());
    }

    private LocalTime slotTime(final int slotId) {
        return LocalTime.of(0, 0).plusMinutes(slotId * this.slotSize);
    }
}





