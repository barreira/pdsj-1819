package controller;

import model.Pair;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

class LocalCalculatorController {

    private enum Operator {
        ADDITION, SUBTRACTION
    }

    private UCalculatorView view;
    private UCalculatorModel model;
    private Stack<String> state;

    LocalCalculatorController() {
        state = new Stack<>();
    }

    void setView(final UCalculatorView view) {
        this.view = view;
    }

    void setModel(final UCalculatorModel model) {
        this.model = model;
    }

    void startFlow() {
        model.clear();
        state.clear();
        String option;
        boolean displayMenu = true;

        do {
            if (displayMenu) {
                view.displayMenu(1);
            }

            view.displayMessage("Insert option: ");
            option = Input.readString();
            displayMenu = true;

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
                    displayMenu = false;
            }
        } while (!option.equals("0"));
    }

    private void localDateCalculator() {
        view.displayMessage("Insert date (dd/MM/yyyy): ");

        final LocalDate localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        state.push(localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        model.initLocalDateCalculator(localDate);
        this.localDateOperationMenu();
    }

    private void localDateOperationMenu() {
        boolean exit = false;
        boolean displayMenu = true;
        String option;

        do {
            if (displayMenu) {
                view.displayMenu(2);
                view.displayMessage(this.stateToString());
            }

            displayMenu = true;
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
                    state.clear();
                    view.displayMessage("Result: ");
                    view.displayLocalDate(model.solve(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    view.displayMessage("Any key to continue: ");
                    Input.readString();
                    exit = true;
                    break;
                case "4":
                    model.previous();
                    state.pop();

                    if (state.empty()) {
                        this.localDateCalculator();
                        exit = true;
                    } else {
                        if (state.peek().equals("+")) {
                            this.durationMenu(Operator.ADDITION);
                        } else {
                            this.durationMenu(Operator.SUBTRACTION);
                        }
                    }

                    break;
                case "0":
                    model.clear();
                    state.clear();
                    break;
                default:
                    displayMenu = false;
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0") && !exit);
    }

    private void durationMenu(Enum<Operator> operation) {
        view.displayMessage("Insert duration: ");

        final int duration = Input.readInt();
        boolean exit = false;
        boolean displayMenu = true;
        String option;

        do {
            if (displayMenu) {
                view.displayMenu(3);
                view.displayMessage(this.stateToString());
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    if (operation == Operator.ADDITION) {
                        model.addDuration(duration, ChronoUnit.DAYS);
                    } else {
                        model.subtractDuration(duration, ChronoUnit.DAYS);
                    }

                    state.push(duration + " days");
                    exit = true;
                    break;
                case "2":
                    if (operation == Operator.ADDITION) {
                        model.addWorkingDays(duration);
                    } else {
                        model.subtractWorkingDays(duration);
                    }

                    state.push(duration + " working days");
                    exit = true;
                    break;
                case "3":
                    if (operation == Operator.ADDITION) {
                        model.addDuration(duration, ChronoUnit.WEEKS);
                    } else {
                        model.subtractDuration(duration, ChronoUnit.WEEKS);
                    }

                    state.push(duration + " weeks");
                    exit = true;
                    break;
                case "4":
                    if (operation == Operator.ADDITION) {
                        model.addFortnights(duration);
                    } else {
                        model.subtractFortnights(duration);
                    }

                    state.push(duration + " fortnights");
                    exit = true;
                    break;
                case "5":
                    if (operation == Operator.ADDITION) {
                        model.addDuration(duration, ChronoUnit.MONTHS);
                    } else {
                        model.subtractDuration(duration, ChronoUnit.MONTHS);
                    }

                    state.push(duration + " months");
                    exit = true;
                    break;
                case "6":
                    if (operation == Operator.ADDITION) {
                        model.addDuration(duration, ChronoUnit.YEARS);
                    } else {
                        model.subtractDuration(duration, ChronoUnit.YEARS);
                    }

                    state.push(duration + " years");
                    exit = true;
                    break;
                case "0":
                    state.pop();
                    exit = true;
                    break;
                default:
                    displayMenu = false;
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0") && !exit);
    }

    private void intervalCalculator() {
        view.displayMessage("Insert first date: ");
        final LocalDate first = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        view.displayMessage("Insert second date: ");
        final LocalDate second = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String option;
        boolean exit = false;

        do {
            view.displayMenu(4);
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.DAYS) + " days");
                    exit = true;
                    break;
                case "2":
                    view.displayMessage(model.intervalInWorkingDays(first, second) + " working days");
                    exit = true;
                    break;
                case "3":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.WEEKS) + " weeks");
                    exit = true;
                    break;
                case "4":
                    view.displayMessage(model.intervalInFortnights(first, second) + " fortnights");
                    exit = true;
                    break;
                case "5":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.MONTHS) + " months");
                    exit = true;
                    break;
                case "6":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.YEARS) + " years");
                    exit = true;
                    break;
                case "7":
                    view.displayPeriod(model.intervalPeriod(first, second));
                    exit = true;
                    break;
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!");
            }
        } while (!option.equals("0") && !exit);
    }

    private void weeksCalculator() {
        String option;

        do {
            view.displayMenu(5);
            option = Input.readString();

            switch (option) {
                case "1":
                    weekNumberOfLocalDate();
                    break;
                case "2":
                    localDateOfWeekNumber();
                    break;
                case "3":
                    daysOfWeekInMonth();
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0"));
    }

    private void weekNumberOfLocalDate() {
        LocalDate localDate;

        view.displayMessage("Insert Date: ");
        localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        view.displayMessage("Week: " + model.getWeekNumber(localDate) + "\n");
    }

    private void localDateOfWeekNumber() {
        view.displayMessage("Insert Week Number: ");
        int weekNumber = Input.readInt();
        view.displayMessage("Insert Year: ");
        int year = Input.readInt();

        Pair<LocalDate, LocalDate> res = model.dateOfWeekNumber(weekNumber, year);
        LocalDate start = res.getFirst();
        LocalDate end = res.getSecond();

        view.displayMessage("Week number " + weekNumber + " of " + year + " starts at " + start.toString() +
                " and ends at " + end.toString() + ".\n");
    }

    private void daysOfWeekInMonth() {
        view.displayMessage("Insert Month: ");
        int month = Input.readInt();
        view.displayMessage("Insert Year: ");
        int year = Input.readInt();

        DayOfWeek dayOfWeek = dayOfWeekMenu();
        if (dayOfWeek == null) return;

        int place = dayOfWeekPlaceMenu();

        if (place >= 1 && place <= 5) {
            List<LocalDate> localDates = model.daysOfWeekInMonth(year, month, dayOfWeek, place);

            if (localDates != null) {
                view.displayLocalDate(localDates.get(0), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            else {
                view.displayMessage(Month.of(month).getDisplayName(TextStyle.FULL, Locale.getDefault()) + " of " +
                                    year + " only has " + (place - 1) + " " +
                                    dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()) + "s");
            }
        }
        else if (place == 6) {
            List<LocalDate> localDates = model.daysOfWeekInMonth(year, month, dayOfWeek, place);

            for (LocalDate ld : localDates) {
                if (ld != null) {
                    view.displayLocalDate(ld, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
            }
        }
    }

    private DayOfWeek dayOfWeekMenu() {
        DayOfWeek dayOfWeek = null;
        boolean exit = false;

        while (!exit) {
            view.displayMenu(6);
            int option = Input.readInt();

            if (option >= 1 && option <= 7) {
                dayOfWeek = DayOfWeek.of(option);
                exit = true;
            }
            else if (option == 0) {
                exit = true;
            }
            else {
                view.displayMessage("Invalid option!\n");
            }
        }

        return dayOfWeek;
    }

    private int dayOfWeekPlaceMenu() {
        int ret = -1;
        boolean exit = false;

        while (!exit) {
            view.displayMenu(7);
            int place = Input.readInt();

            if (place >= 0 && place <= 6) {
                ret = place;
                exit = true;
            }
            else {
                view.displayMessage("Invalid option!\n");
            }
        }

        return ret;
    }

    private String stateToString() {
        final StringBuilder s = new StringBuilder("State: ");

        state.forEach(str -> { s.append(" "); s.append(str); });

        s.append("\n");

        return s.toString();
    }
}