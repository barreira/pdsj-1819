package controller;

import model.Slot;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ScheduleController {
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
        do {
            view.displayMenu(9);
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    this.add();
                    break;
                case "2":
                    this.edit();
                    break;
                case "3":
                    this.consult();
                    break;
                case "4":
                    this.remove();
                    break;
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0"));
    }

    private void add() {
        view.displayMessage("Insert description: ", true);
        String description = Input.readString();
        view.displayMessage("Insert start date (dd/MM/yyyy HH:mm): ");
        LocalDateTime start = Input.readDateTime(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        view.displayMessage("Insert end date (dd/MM/yyyy HH:mm): ");

        LocalDateTime end = Input.readDateTime(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        System.out.println(model.addSlot(description, start, end));
    }

    private void edit() {

    }

    private void consult() {
        view.displayMessage("Insert date (dd/MM/yyyy): ");
        LocalDate localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Paging paging = new Paging(model.consult(localDate).stream().map(Slot::toString).collect(Collectors.toList()), 3);
        String option;

        view.displayPage(paging.currentPage(), paging.getCurrentPage(), paging.getTotalPages());

        do {
            view.displayMessage("Previous - p\n");
            view.displayMessage("Next ----- n\n");
            view.displayMessage("Cancel --- 0\n");
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "p":
                    view.displayPage(paging.nextPage(), paging.getCurrentPage(), paging.getTotalPages());
                    break;
                case "n":
                    view.displayPage(paging.previousPage(), paging.getCurrentPage(), paging.getTotalPages());
                    break;
                case "0":
                    return;
                default:
                    view.displayMessage("Invalid option!");
            }
        } while (!option.equals("0"));
    }

    private void remove() {

    }
}
