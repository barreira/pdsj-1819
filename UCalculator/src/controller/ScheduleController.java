package controller;

import model.Slot;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
                    this.consultEdit();
                    break;
                case "3":
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
        view.displayMessage("Insert date (dd/MM/yyyy): ");

        final LocalDate localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        final List<Slot> slots = model.consult(localDate);
        int slotId;
        int duration;

        view.displaySlots(localDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("HH:mm"), slots);
        view.displayMessage("Select Slot number: ");

        do {
            slotId = Input.readInt();

            if (slotId < 0 || slotId >= slots.size()) {
                view.displayMessage("Invalid slot! Try again: ");
            }
        } while (slotId < 0 || slotId >= slots.size());

        view.displayMessage("Number of slots: ");

        do {
            duration = Input.readInt();

            if (duration <= 0) {
                view.displayMessage("Invalid number of slots! Try again: ");
            }
        } while (duration <= 0);

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

        if (model.addTask(localDate, slotId, duration, title, people)) {
            view.displayMessage("Task added!\n");
        } else {
            view.displayMessage("Could not add task...\n");
        }

        this.stopExecution();
    }

    private void consultEdit() {

    }

    private void remove() {

    }

    private void stopExecution() {
        view.displayMessage("Press enter to continue: ");
        Input.readString();
    }
}
