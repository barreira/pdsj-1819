package controller;

import model.UCalculatorModel;
import model.UtilitaryDate;
import view.Menu;
import view.UCalculatorView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                        this.durationMenu(Operation.ADDITION);
                        break;
                    case "2":
                        stack.push("-");
                        this.durationMenu(Operation.SUBTRACTION);
                        break;
                    case "3":
                        System.out.println(model.solve());
                        break;
                    case "4":
                        // TODO: this function logic
                        model.previous();
                        break;
                    case "0":
                        // TODO: Cancel logic
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } while (!option.equals("0") && !option.equals("4"));
        }
        localDateCalculator();
    }

    private void durationMenu(Enum<Operation> operation) {
        System.out.print("Insert duration: ");
        int duration = Input.readInt();
        // BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction = operation == Operation.ADDITION ? UtilitaryDate.datePlusDuration : UtilitaryDate.dateMinusDuration;

        Menu menu = view.getMenu(3);
        boolean exit = false;
        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction = operation == Operation.ADDITION ? UtilitaryDate.datePlusDuration : UtilitaryDate.dateMinusDuration;
                        model.next(biFunction, Duration.ofDays(duration));
                        stack.push(duration + " days");
                        exit = true;
                        break;
                    case "2":
                        // TODO: create BiFunction to subtract working days
                        model.next(UtilitaryDate.datePlusWorkingDays, duration);
                        stack.push(duration + " working days");
                        exit = true;
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
            } while (!option.equals("0") && !exit);
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

    private enum Operation {
        ADDITION, SUBTRACTION
    }
}
