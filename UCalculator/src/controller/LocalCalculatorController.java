package controller;

import model.UCalculatorModel;
import model.Config;
import view.UCalculatorView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Stack;
import java.util.AbstractMap.SimpleEntry;

class LocalCalculatorController {
    private enum Operator {
        ADDITION, SUBTRACTION
    }

    private final Stack<String> state;
    private UCalculatorView view;
    private UCalculatorModel model;

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
        view.displayMessage("Insert date (" + model.getDatePattern() + "): ");

        final LocalDate localDate = Input.readDate(DateTimeFormatter.ofPattern(model.getDatePattern()));

        state.push(localDate.format(DateTimeFormatter.ofPattern(model.getDatePattern())));
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
                    final LocalDate result = model.solve();

                    state.clear();
                    view.displayMessage("Result: ");

                    if (result != null) {
                        view.displayLocalDate(result, DateTimeFormatter.ofPattern(model.getDatePattern()));
                    } else {
                        // Este caso nunca vai ocorrer devido ao fluxo implementado
                        view.displayMessage("Something went wrong...\n");
                    }

                    model.clear();
                    this.stopExecution();
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
        view.displayMessage("Insert first date (" + model.getDatePattern() + "): ");

        final LocalDate first = Input.readDate(DateTimeFormatter.ofPattern(model.getDatePattern()));

        view.displayMessage("Insert second date (" + model.getDatePattern() + "): ");

        final LocalDate second = Input.readDate(DateTimeFormatter.ofPattern(model.getDatePattern()));
        String option;
        boolean exit = false;
        boolean displayMenu = true;

        do {
            if (displayMenu) {
                view.displayMenu(4);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.DAYS) + " days\n");
                    exit = true;
                    break;
                case "2":
                    view.displayMessage(model.intervalInWorkingDays(first, second) + " working days\n");
                    exit = true;
                    break;
                case "3":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.WEEKS) + " weeks\n");
                    exit = true;
                    break;
                case "4":
                    view.displayMessage(model.intervalInFortnights(first, second) + " fortnights\n");
                    exit = true;
                    break;
                case "5":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.MONTHS) + " months\n");
                    exit = true;
                    break;
                case "6":
                    view.displayMessage(model.intervalInUnit(first, second, ChronoUnit.YEARS) + " years\n");
                    exit = true;
                    break;
                case "7":
                    view.displayPeriod(model.intervalPeriod(first, second));
                    exit = true;
                    break;
                case "0":
                    break;
                default:
                    displayMenu = false;
                    view.displayMessage("Invalid option!\n");
            }

            if (!option.equals("0") && exit) {
                this.stopExecution();
            }
        } while (!option.equals("0") && !exit);
    }

    private void weeksCalculator() {
        String option;
        boolean displayMenu = true;

        do {
            if (displayMenu) {
                view.displayMenu(5);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
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
                    displayMenu = false;
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0"));
    }

    private void weekNumberOfLocalDate() {
        LocalDate localDate;

        view.displayMessage("Insert Date (" + model.getDatePattern() + "): ");
        localDate = Input.readDate(DateTimeFormatter.ofPattern(model.getDatePattern()));
        view.displayMessage("Week: " + model.getWeekNumber(localDate) + "\n");
        this.stopExecution();
    }

    private void localDateOfWeekNumber() {
        view.displayMessage("Insert Week Number: ");
        final int weekNumber = Input.readInt();

        view.displayMessage("Insert Year: ");

        final int year = Input.readInt();
        final SimpleEntry<LocalDate, LocalDate> res = model.dateOfWeekNumber(weekNumber, year);
        final LocalDate start = res.getKey();
        final LocalDate end = res.getValue();

        view.displayMessage("Week number " + weekNumber + " of " + year + " starts at " + start.toString() +
                " and ends at " + end.toString() + ".\n");
        this.stopExecution();
    }

    private void daysOfWeekInMonth() {
        view.displayMessage("Insert Month: ");

        final int month = Input.readInt();

        view.displayMessage("Insert Year: ");

        final int year = Input.readInt();
        final DayOfWeek dayOfWeek = dayOfWeekMenu();

        if (dayOfWeek == null) {
            return;
        }

        final int place = dayOfWeekPlaceMenu();

        if (place >= 1 && place <= 5) {
            final LocalDate localDate = model.dayOfWeekInMonth(year, month, dayOfWeek, place);

            if (localDate != null) {
                view.displayLocalDate(localDate, DateTimeFormatter.ofPattern(model.getDatePattern()));
            } else {
                view.displayMessage("This month only has " + (place - 1) + " " + dayOfWeek + "S\n");
            }

            this.stopExecution();
        } else if (place == 6) {
            final List<LocalDate> localDates = model.getAllDaysOfWeekInMonth(year, month, dayOfWeek);

            localDates.forEach(l -> view.displayLocalDate(l, DateTimeFormatter.ofPattern(model.getDatePattern())));

            this.stopExecution();
        }
    }

    private DayOfWeek dayOfWeekMenu() {
        DayOfWeek dayOfWeek = null;
        boolean exit = false;
        boolean displayMenu = true;

        while (!exit) {
            if (displayMenu) {
                view.displayMenu(6);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            int option = Input.readInt();

            if (option >= 1 && option <= 7) {
                dayOfWeek = DayOfWeek.of(option);
                exit = true;
            }
            else if (option == 0) {
                exit = true;
            }
            else {
                displayMenu = false;
                view.displayMessage("Invalid option!\n");
            }
        }

        return dayOfWeek;
    }

    private int dayOfWeekPlaceMenu() {
        int ret = -1;
        boolean exit = false;
        boolean displayMenu = true;

        while (!exit) {
            if (displayMenu) {
                view.displayMenu(7);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            int place = Input.readInt();

            if (place >= 0 && place <= 6) {
                ret = place;
                exit = true;
            }
            else {
                displayMenu = false;
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

    private void stopExecution() {
        view.displayMessage("Press ENTER to continue... ");
        Input.readString();
    }
}