package controller;

import model.DateUtils;
import model.Pair;
import model.UCalculatorModel;
import view.Menu;
import view.UCalculatorView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Stack;

public class UCalculatorController {

    private enum Operator {
        ADDITION, SUBTRACTION
    }

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
                view.displayMessage("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        localDateCalculatorMenu();
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
                        view.displayMessage("Invalid option!\n");
                }
            } while (!option.equals("0"));
        }
    }

    private void localDateCalculatorMenu() {
        Menu menu = view.getMenu(1);

        model.clear();
        state.clear();

        if (menu != null) {
            String option;
            do {
                menu.show();
                view.displayMessage("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        localDateCalculator();
                        break;
                    case "2":
                        intervalCalculator();
                        break;
                    case "3":
                        weeksCalculator();
                        break;
                    case "0":
                        break;
                    default:
                        view.displayMessage("Invalid option!\n");
                }
            } while (!option.equals("0"));
        }
    }

    private void localDateCalculator() {
        view.displayMessage("Insert date: ");
        LocalDate localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        state.push(localDate.toString());
        model.next(localDate);
        this.localDateOperationMenu();
    }

    private void localDateOperationMenu() {
        Menu menu = view.getMenu(2);

        if (menu != null) {
            boolean exit = false;
            String option;

            do {
                this.showSpacing();
                menu.show();
                view.displayMessage(this.stateToString());
                view.displayMessage("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        state.push("+");
                        this.durationMenu(Operator.ADDITION);
                        break;
                    case "2":
                        state.push("-");
                        this.durationMenu(Operator.SUBTRACTION);
                        break;
                    case "3":
                        this.showSpacing();
                        view.displayMessage("Result: ");
                        view.displayLocalDate(model.solve(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        exit = true;
                        break;
                    case "4":
                        model.previous();
                        state.pop();

                        if (state.empty()) {
                            this.localDateCalculator();
                        } else {
                            if (state.peek().equals("+")) {
                                this.durationMenu(Operator.ADDITION);
                            } else {
                                this.durationMenu(Operator.SUBTRACTION);
                            }
                        }

                        exit = true;
                        break;
                    case "0":
                        break;
                    default:
                        view.displayMessage("Invalid option!\n");
                }
            } while (!option.equals("0") && !exit);
        }
    }

    private void durationMenu(Enum<Operator> operation) {
        view.displayMessage("Insert duration: ");

        final int duration = Input.readInt();
        final Menu menu = view.getMenu(3);
        boolean exit = false;

        if (menu != null) {
            String option;

            do {
                this.showSpacing();
                menu.show();
                view.displayMessage(this.stateToString());
                view.displayMessage("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        model.next(operation == Operator.ADDITION ?
                                DateUtils.datePlusDuration : DateUtils.dateMinusDuration, duration, ChronoUnit.DAYS);
                        state.push(duration + " days");
                        exit = true;
                        break;
                    case "2":
                        model.next(operation == Operator.ADDITION ?
                                DateUtils.datePlusWorkingDays : DateUtils.dateMinusWorkingDays, duration);
                        state.push(duration + " working days");
                        exit = true;
                        break;
                    case "3":
                        model.next(operation == Operator.ADDITION ? 
                                DateUtils.datePlusDuration : DateUtils.dateMinusDuration, duration, ChronoUnit.WEEKS);
                        state.push(duration + " weeks");
                        exit = true;
                        break;
                    case "4":
                        model.next(operation == Operator.ADDITION ?
                                DateUtils.datePlusFortnights : DateUtils.dateMinusFortnights, duration);
                        state.push(duration + " fortnights");
                        exit = true;
                        break;
                    case "5":
                        model.next(operation == Operator.ADDITION ?
                                DateUtils.datePlusDuration : DateUtils.dateMinusDuration, duration, ChronoUnit.MONTHS);
                        state.push(duration + " months");
                        exit = true;
                        break;
                    case "6":
                        model.next(operation == Operator.ADDITION ?
                                DateUtils.datePlusDuration : DateUtils.dateMinusDuration, duration, ChronoUnit.YEARS);
                        state.push(duration + " years");
                        exit = true;
                        break;
                    case "0":
                        state.pop();
                        exit = true;
                        break;
                    default:
                        view.displayMessage("Invalid option!\n");
                }
            } while (!option.equals("0") && !exit);
        }
    }

    private void intervalCalculator() {
        view.displayMessage("Insert first date: ");
        final LocalDate first = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        view.displayMessage("Insert second date: ");
        final LocalDate second = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        final Menu menu = view.getMenu(4);

        if (menu != null) {
            String option;
            boolean exit = false;

            do {
                view.displaySpacing();
                menu.show();
                view.displayMessage("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        view.displayMessage(DateUtils.intervalInUnit(first, second, ChronoUnit.DAYS) + " days");
                        exit = true;
                        break;
                    case "2":
                        view.displayMessage(DateUtils.intervalInWorkingDays(first, second) + " working days");
                        exit = true;
                        break;
                    case "3":
                        view.displayMessage(DateUtils.intervalInUnit(first, second, ChronoUnit.WEEKS) + " weeks");
                        exit = true;
                        break;
                    case "4":
                        view.displayMessage(DateUtils.intervalInFortnights(first, second) + " fortnights");
                        exit = true;
                        break;
                    case "5":
                        view.displayMessage(DateUtils.intervalInUnit(first, second, ChronoUnit.MONTHS) + " months");
                        exit = true;
                        break;
                    case "6":
                        view.displayMessage(DateUtils.intervalInUnit(first, second, ChronoUnit.YEARS) + " years");
                        exit = true;
                        break;
                    case "7":
                        view.displayPeriod(DateUtils.intervalBetweenDates.apply(first, second));
                        exit = true;
                        break;
                    case "0":
                        break;
                    default:
                        view.displayMessage("Invalid option!");
                }
            } while (!option.equals("0") && !exit);
        }
    }

    private void weeksCalculator() {
        Menu menu = view.getMenu(5);

        if (menu != null) {
            String option;

            do {
                this.showSpacing();
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
                        view.displayMessage("Invalid option!\n");
                }
            } while (!option.equals("0"));
        }
    }

    private void weekNumberOfLocalDate() {
        LocalDate localDate;

        view.displayMessage("Insert Date: ");
        localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        view.displayMessage("Week: " + DateUtils.weekNumberOfLocalDate(localDate) + "\n");
    }

    private void localDateOfWeekNumber() {
        view.displayMessage("Insert Week Number: ");
        int weekNumber = Input.readInt();
        view.displayMessage("Insert Year: ");
        int year = Input.readInt();

        Pair<LocalDate, LocalDate> res = DateUtils.localDateOfYearWeekNumber(weekNumber, year);
        LocalDate start = res.getFirst();
        LocalDate end = res.getSecond();

        view.displayMessage("Week number " + weekNumber + " of " + year + " starts at " + start.toString()
                           + " and ends at " + end.toString() + ".\n");
    }

    private void timeZoneDateTimeCalculator() {
        System.out.println("TIME ZONE DATE TIME CALCULATOR HERE");
    }

    private void meetingSchedule() {
        System.out.println("MEETING SCHEDULE");
    }

    private String stateToString() {
        final StringBuilder s = new StringBuilder("State: ");

        for (Object o : state.toArray()) {
            s.append(" ");
            s.append(o);
        }

        s.append("\n");

        return s.toString();
    }

    private void showSpacing() {
        System.out.print("\n".repeat(10));
    }
}
