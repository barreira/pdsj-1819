package controller;

import model.Slot;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

class ScheduleController {
    private UCalculatorView view;
    private UCalculatorModel model;

    void setView(UCalculatorView view) {
        this.view = view;
    }

    void setModel(UCalculatorModel model) {
        this.model = model;
    }

    void startFlow() {
        String option;
        boolean displayMenu = true;

        do {
            if (displayMenu) {
                view.displayMenu(9);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    this.add();
                    break;
                case "2":
                    this.openSlots();
                    break;
                case "3":
                    this.closeSlots();
                    break;
                case "4":
                    this.consultEdit();
                    break;
                case "5":
                    this.remove();
                    break;
                case "0":
                    break;
                default:
                    displayMenu = false;
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0"));
    }

    private void add() {
        final SimpleEntry<LocalDate, List<Slot>> entry = this.getSlotsOfDay();
        final int slotId = this.getSlotId(entry.getValue());
        int duration = this.getDuration();

        view.displayMessage("Insert Title: ");

        final String title = Input.readString();
        final List<String> people = new ArrayList<>();
        String p;

        do {
            view.displayMessage("Add people (0 to finish): ");
            p = Input.readString();

            if (!p.equals("0")) {
                people.add(p);
            }
        } while (!p.equals("0"));

        if (model.addTask(entry.getKey(), slotId, duration, title, people)) {
            view.displayMessage("Task added!\n");
        } else {
            view.displayMessage("Could not add task...\n");
        }

        this.stopExecution();
    }

    private void openSlots() {
        final SimpleEntry<LocalDate, List<Slot>> entry = this.getSlotsOfDay();
        final int slotId = this.getSlotId(entry.getValue());
        final int duration = this.getDuration();

        view.displayMessage(model.openSlots(entry.getKey(), slotId, duration) + " Slots opened!\n");

        this.stopExecution();
    }

    private void closeSlots() {
        final SimpleEntry<LocalDate, List<Slot>> entry = this.getSlotsOfDay();
        final int slotId = this.getSlotId(entry.getValue());
        final int duration = this.getDuration();

        if (model.closeSlots(entry.getKey(), slotId, duration)) {
            view.displayMessage("Slots closed!\n");
        } else {
            view.displayMessage("Could not close slots...\n");
        }

        this.stopExecution();
    }

    private void consultEdit() {

    }

    private void remove() {
        final SimpleEntry<LocalDate, List<Slot>> entry = this.getSlotsOfDay();
        final int slotId = this.getSlotId(entry.getValue());

        if (model.removeTask(entry.getKey(), slotId)) {
            view.displayMessage("Task removed!\n");
        } else {
            view.displayMessage("Could not remove task...\n");
        }

        this.stopExecution();
    }

    private SimpleEntry<LocalDate, List<Slot>> getSlotsOfDay() {
        view.displayMessage("Insert date (" + model.getDatePattern() + "): ");

        final LocalDate localDate = Input.readDate(DateTimeFormatter.ofPattern(model.getDatePattern()));
        final List<Slot> slots = model.consult(localDate);

        view.displaySlots(localDate, DateTimeFormatter.ofPattern(model.getDatePattern()),
                DateTimeFormatter.ofPattern(model.getTimePattern()), slots);

        return new SimpleEntry<>(localDate, slots);
    }

    private int getSlotId(final List<Slot> slots) {
        int slotId;

        view.displayMessage("Select Slot number: ");

        do {
            slotId = Input.readInt();

            if (slotId < 0 || slotId >= slots.size()) {
                view.displayMessage("Invalid slot! Try again: ");
            }
        } while (slotId < 0 || slotId >= slots.size());

        return slotId;
    }

    private int getDuration() {
        int duration;

        view.displayMessage("Number of slots: ");

        do {
            duration = Input.readInt();

            if (duration <= 0) {
                view.displayMessage("Invalid number of slots! Try again: ");
            }
        } while (duration <= 0);

        return duration;
    }

    private void stopExecution() {
        view.displayMessage("Press ENTER to continue... ");
        Input.readString();
    }
}
