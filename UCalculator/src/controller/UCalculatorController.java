package controller;

import model.DateUtils;
import model.Pair;
import model.UCalculatorModel;
import view.Menu;
import view.UCalculatorView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import java.util.function.BiFunction;

public class UCalculatorController {

    private UCalculatorView view;
    private UCalculatorModel model;
    private Stack<String> state;

    public UCalculatorController() {
        state = new Stack<>();
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
                System.out.print("Insert option: ");
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
                System.out.print("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        localDateCalculator();
                        break;
                    case "2":
                        intervalCalculator();
                        break;
                    case "3":
                        timeUnitsCalculator();
                        break;
                    case "4":
                        weeksCalculator();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } while (!option.equals("0"));
        }
    }

    // private void localDateCalculator() {
    //    state.clear();
    //    this.localDateCalculator();
    // }

    private void localDateCalculator() {
        System.out.print("Insert date: ");
        LocalDate localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        state.push(localDate.toString());
        model.next(localDate.atTime(0, 0));
        this.localDateOperationMenu();
    }

    private void localDateOperationMenu() {
        Menu menu = view.getMenu(2);

        if (menu != null) {
            String option;
            boolean exit = false;
            do {
                menu.show();
                this.show();
                System.out.print("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        state.push("+");
                        this.durationMenu(Operation.ADDITION);
                        break;
                    case "2":
                        state.push("-");
                        this.durationMenu(Operation.SUBTRACTION);
                        break;
                    case "3":
                        state.clear();
                        System.out.println("Result: " + model.solve().format(DateTimeFormatter.ISO_LOCAL_DATE));
                        exit = true;
                        break;
                    case "4":
                        // TODO: review usage of this operation
                        model.previous();
                        state.pop();
                        break;
                    case "0":
                        // TODO: Cancel logic
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } while (!(option.equals("0") && !exit));
        }
        localDateTimeCalculator();
    }

    private void durationMenu(Enum<Operation> operation) {
        System.out.print("Insert duration: ");
        int duration = Input.readInt();
        // BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction = operation == Operation.ADDITION ? DateUtils.datePlusDuration : DateUtils.dateMinusDuration;

        Menu menu = view.getMenu(3);
        boolean exit = false;
        if (menu != null) {
            String option;

            do {
                menu.show();
                this.show();
                System.out.print("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction =
                                operation == Operation.ADDITION ?
                                        DateUtils.datePlusDuration : DateUtils.dateMinusDuration;

                        model.next(biFunction, Duration.ofDays(duration));
                        state.push(duration + " days");
                        exit = true;
                        break;
                    case "2":
                        // TODO: create BiFunction to subtract working days
                        model.next(DateUtils.datePlusWorkingDays, duration);
                        state.push(duration + " working days");
                        exit = true;
                        break;
                    case "3":
                        break;
                    case "4":
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } while (!option.equals("0") && !exit);
        }
    }

    private void intervalCalculator() {
        System.out.println("INTERVAL CALCULATOR");
    }

    private void timeUnitsCalculator() {
        System.out.println("TIME UNITS CALCULATOR");
    }

    private void weeksCalculator() {
        Menu menu = view.getMenu(5);

        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        weekNumberOfLocalDate();
                        break;
                    case "2":
                        localDateOfWeekNumber();
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } while (!option.equals("0"));
        }
    }

    private void weekNumberOfLocalDate() {
        LocalDateTime localDateTime;

        System.out.println("Insert Date: ");
        localDateTime = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy")).atTime(0, 0);
        System.out.println(DateUtils.weekNumberOfLocalDate(localDateTime));
    }

    private void localDateOfWeekNumber() {
        System.out.println("Insert Week Number: ");
        int weekNumber = Input.readInt();
        System.out.println("Insert Year: ");
        int year = Input.readInt();

        Pair<LocalDateTime, LocalDateTime> res = DateUtils.localDateOfYearWeekNumber(weekNumber, Year.of(year));
        LocalDateTime start = res.getFirst();
        LocalDateTime end = res.getSecond();

        System.out.println("Week number " + weekNumber + " of " + year + " starts at " + start.toLocalDate().toString()
                           + " and ends at " + end.toLocalDate().toString() + ".");
    }

    private void timeZoneDateTimeCalculator() {
        System.out.println("TIME ZONE DATE TIME CALCULATOR HERE");
    }

    private void meetingSchedule() {
        System.out.println("MEETING SCHEDULE");
    }

    private void show() {
        System.out.print("State:");
        for (Object o : state.toArray()) {
            System.out.print(" " + o);
        }
        System.out.println();
    }

    private enum Operation {
        ADDITION, SUBTRACTION
    }
}
