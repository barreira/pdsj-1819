package controller;

import model.*;
import view.Menu;
import view.UCalculatorView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Stack;
import java.util.function.BiFunction;

public class UCalculatorController {

    private UCalculatorView view;
    private UCalculatorModel model;
    private Stack<String> stack;


    public UCalculatorController() {
        stack = new Stack<>();
    }

    public void setView(final UCalculatorView view) {
        this.view = view;
    }


    public void setModel(final UCalculatorModel model) {
        this.model = model;
    }


    public void startFlow() {
        Menu menu = view.getMenu(0);

        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        localDateTimeCalculator();
                        break;
                    case "2":
                        timeZoneDateTimeCalculator();
                        break;
                    case "3":
                        meetingSchedule();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } while (!option.equals("0"));
        }
    }

    private void localDateTimeCalculator() {
        Menu menu = view.getMenu(1);

        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        localDateCalculator();
                        break;
                    case "2":
                        weeksCalculator();
                        break;
                    case "3":
                        timeUnitsCalculator();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } while (!option.equals("0"));
        }
    }

    private void localDateCalculator() {
        stack.clear();
        this.localDateMenu();
    }


    private void localDateMenu() {
        LocalDate localDate;

        System.out.println("Insert Date: ");
        localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        stack.push(localDate.toString());
        model.next(LocalDateTime.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), 0, 0, 0));
        this.localDateOperationMenu();
    }



    private void localDateOperationMenu() {
        Menu menu = view.getMenu(2);

        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        stack.push("+");
                        //TODO: passar para ENUM
                        this.durationMenu("plus");
                        break;
                    case "2":
                        stack.push("-");
                        break;
                    case "3":
                        System.out.println(model.compute());
                        break;
                    case "4":
                        // TODO:
                        model.previous();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } while (!option.equals("0") && !option.equals("4"));
        }
        localDateCalculator();
    }

    private void durationMenu(String operation) {
        System.out.println("Insert Duration: ");
        int i = Input.readInt();
        Menu menu = view.getMenu(3);
        BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction = operation.equals("plus") ? UtilitaryDate.datePlusDuration : UtilitaryDate.dateMinusDuration;

        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        stack.push(i + " days");
                        model.next(biFunction, Duration.ofDays(i));
                        return;
                    case "2":
                        stack.push(i + " working days");
                        model.next(UtilitaryDate.datePlusWorkingDays, i);
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } while (!option.equals("0"));
        }
    }


    // TODO: João
    private void weeksCalculator() {
        System.out.println("WEEKS CALCULATOR HERE");
    }

    private void timeUnitsCalculator() {
        System.out.println("TIME UNITS CALCULATOR");
    }

    private void timeZoneDateTimeCalculator() {
        System.out.println("TIME ZONE DATE TIME CALCULATOR HERE");
    }

    private void meetingSchedule() {
        System.out.println("MEETING SCHEDULE");
    }
}
