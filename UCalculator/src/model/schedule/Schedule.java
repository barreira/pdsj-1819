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
        this.startSlotId = 0;
        this.endSlotId = 1440 / this.slotSize - 1;
        this.taskId = 0;
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

        if (slotId < startSlotId || slotId > endSlotId || duration < 1) {
            success = false;
        } else {
            LocalDate next = date;
            int k = slotId;
            List<Slot> slots;

            for (int i = 0; i < duration && success; i++) {
                if ((slots = schedule.get(next)) != null) {
                    for (int j = k; j <= endSlotId && j <= i; j++) {
                        if (!slots.get(j).getClass().equals(OpenSlot.class)) {
                            success = false;
                            break;
                        }
                    }
                } else {
                    i += endSlotId - startSlotId;
                }

                next = next.plusDays(1);
                k = startSlotId;
            }

            if (success) {
                final Task task = new Task(taskId++, date, slotId, duration, title, people);
                k = slotId;
                next = date;

                for (int i = 0; i < duration; i++) {
                    if ((slots = schedule.get(next)) == null) {
                        slots = new ArrayList<>();

                        for (int j = 0; j < totalSlots; j++) {
                            slots.add(new OpenSlot(j, this.slotTime(j), this.slotTime(j + 1)));
                        }

                        this.schedule.put(next, slots);
                    }

                    for (int j = k; j <= endSlotId && i < duration; j++, i++) {
                        slots.set(j, new BusySlot(j, this.slotTime(j),
                                this.slotTime(j + 1), task));
                    }

                    next = next.plusDays(1);
                    k = startSlotId;
                    i--;
                }
            }
        }

        return success;
    }

    public boolean addTask(final LocalDate date, final int slotId, final String title, final List<String> people) {
        return addTask( date, slotId, 1, title, people);
    }

    public Task removeTask(final LocalDate date, final int slotId) {
        Task task = null;

        if (slotId >= startSlotId && slotId <= endSlotId) {
            List<Slot> slots = schedule.get(date);
            Slot slot;

            if (slots != null && (slot = slots.get(slotId)).getClass().equals(BusySlot.class)) {
                task = ((BusySlot) slot).getTask();
                int k = task.getSlotId();
                LocalDate next = task.getDate();

                for (int i = 0; i < task.getDuration(); i++) {
                    slots = schedule.get(next);

                    for (int j = k; j <= endSlotId && i < task.getDuration(); j++, i++) {
                        slots.set(j, new OpenSlot(j, this.slotTime(j), this.slotTime(j + 1)));
                    }

                    i--;
                    k = startSlotId;
                    next = next.plusDays(1);
                }
            }
        }

        return task;
    }

    public boolean editTask(final LocalDate date, final int slotId, final LocalDate newDate,
                            final int newSlotId, final int newDuration) {
        boolean success = true;

        if (newDuration >= 1 && newSlotId >= startSlotId && newSlotId <= endSlotId) {
            final Task task = this.removeTask(date, slotId);

            if (task == null) {
                success = false;
            } else if (!this.addTask(newDate, newSlotId, newDuration, task.getTitle(), task.getPeople())) {
                success = !this.addTask(task);
            }
        } else {
            success = false;
        }

        return success;
    }

    public boolean editTask(final LocalDate date, final int slotId, final LocalDate newDate, final String title, final List<String> people) {
        boolean success = false;
        List<Slot> slots;

        if (slotId >= startSlotId && slotId <= endSlotId && (slots = this.schedule.get(date)) != null) {
            if (slots.get(slotId).getClass().equals(BusySlot.class)) {
                final Task task = ((BusySlot)slots.get(slotId)).getTask();

                task.setTitle(title);
                task.setPeople(people);
                success = true;
            }
        }

        return success;
    }

    public List<Slot> consult(final LocalDate date) {
        final List<Slot> result = new ArrayList<>();
        final List<Slot> slots = schedule.get(date);


        if (slots == null) {
            for (int i = startSlotId; i <= endSlotId; i++) {
                result.add(new OpenSlot(i, this.slotTime(i), this.slotTime(i + 1)));
            }
        } else {
            for (int i = startSlotId; i <= endSlotId; i++) {
                result.add(slots.get(i).clone());
            }
        }

        return result;
    }

    private boolean addTask(Task task) {
        return this.addTask(task.getDate(), task.getSlotId(), task.getDuration(), task.getTitle(), task.getPeople());
    }

    private LocalTime slotTime(final int slotId) {
        return LocalTime.of(0, 0).plusMinutes(slotId * this.slotSize);
    }
}





