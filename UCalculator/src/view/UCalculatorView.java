package view;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public final class UCalculatorView {

    private final Map<Integer, Menu> menus;

    public UCalculatorView() {
        menus = Map.ofEntries(
                entry(0, new Menu("\t\t*** MAIN MENU ***", Arrays.asList(
                        new Option("Local Date Time Calculator ----", "1"),
                        new Option("Timezone Date Time Calculator -", "2"),
                        new Option("Schedule ----------------------", "3"),
                        new Option("End Session -------------------", "0")))),
                entry(1, new Menu("*** LOCAL DATE TIME CALCULATOR ***", Arrays.asList(
                        new Option("Date Calculator -----", "1"),
                        new Option("Interval Calculator -", "2"),
                        new Option("Weeks Calculator ----", "3"),
                        new Option("Back ----------------", "0")))),
                entry(2, new Menu("*** SELECT OPERATION ***", Arrays.asList(
                        new Option("Add ------", "1"),
                        new Option("Subtract -", "2"),
                        new Option("Solve ----", "3"),
                        new Option("Back -----", "4"),
                        new Option("Cancel ---", "0")))),
                entry(3, new Menu("*** SELECT DURATION UNITS ***", Arrays.asList(
                        new Option("Days ---------", "1"),
                        new Option("Working Days -", "2"),
                        new Option("Weeks --------", "3"),
                        new Option("Fortnights ---", "4"),
                        new Option("Months -------", "5"),
                        new Option("Years --------", "6"),
                        new Option("Back ---------", "0")))),
                entry(4, new Menu("*** SELECT RESULT UNITS ***", Arrays.asList(
                        new Option("Days ---------", "1"),
                        new Option("Working Days -", "2"),
                        new Option("Weeks --------", "3"),
                        new Option("Fortnights ---", "4"),
                        new Option("Months -------", "5"),
                        new Option("Years --------", "6"),
                        new Option("Period -------", "7"),
                        new Option("Cancel -------", "0")))),
                entry(5, new Menu("\t*** Weeks Calculator ***", Arrays.asList(
                        new Option("Week number of Date -------", "1"),
                        new Option("Date of Week number -------", "2"),
                        new Option("Days of the Week in Month -", "3"),
                        new Option("Back ----------------------", "0")))),
                entry(6, new Menu("*** SELECT DAY OF THE WEEK ***", Arrays.asList(
                        new Option("Monday ----", "1"),
                        new Option("Tuesday ---", "2"),
                        new Option("Wednesday -", "3"),
                        new Option("Thursday --", "4"),
                        new Option("Friday ----", "5"),
                        new Option("Saturday --", "6"),
                        new Option("Sunday ----", "7"),
                        new Option("Cancel ----", "0")))),
                entry(7, new Menu("*** SELECT PLACE ***", Arrays.asList(
                        new Option("First --", "1"),
                        new Option("Second -", "2"),
                        new Option("Third --", "3"),
                        new Option("Fourth -", "4"),
                        new Option("Fifth --", "5"),
                        new Option("All ----", "6"),
                        new Option("Cancel -", "0")))),
                entry(8, new Menu("*** Timezone Calculator ***", Arrays.asList(
                        new Option("Timezone Converter -", "1"),
                        new Option("Travel Calculator --", "2"),
                        new Option("Back ---------------", "0")))),
                entry(19, new Menu("\t*** ADD CONNECTION ***", Arrays.asList(
                        new Option("Add Connection --------------", "1"),
                        new Option("Calculate Total Travel Time -", "2"),
                        new Option("Cancel ----------------------", "0")))),
                entry(9, new Menu("*** SCHEDULER MANAGER ***", Arrays.asList(
                        new Option("Add ----------", "1"),
                        new Option("Open Slots ---", "2"),
                        new Option("Close Slots --", "3"),
                        new Option("Consult/Edit -", "4"),
                        new Option("Remove -------", "5"),
                        new Option("Back ---------", "0")))),
                entry(10, new Menu("*** Edit Task ***", Arrays.asList(
                        new Option("Change Title ----", "1"),
                        new Option("Change Slot -----", "2"),
                        new Option("Change Duration -", "3"),
                        new Option("Change People ---", "4"),
                        new Option("Commit Changes --", "5"),
                        new Option("Back ------------", "0"))))
        );
    }

    public void displayMessage(final String message) {
        System.out.print(message);
    }

    public void displayLocalDate(final LocalDate date, final DateTimeFormatter formatter) {
        System.out.println(date.format(formatter));
    }

    public void displayLocalDateTime(final LocalDateTime datetime, final DateTimeFormatter formatter) {
        System.out.print(datetime.format(formatter));
    }

    public void displayPeriod(final Period period) {
        System.out.println(period.getYears() + " years, " + period.getMonths() + " months, and " +
                period.getDays() + " days");
    }

    public void displayPage(final List<String> elements, final int currentPage, final int totalPages) {
        this.displaySpacing();

        int i = 1;

        for(String element : elements) {
            System.out.println(element + " " + i);
            i++;
        }

        System.out.println("Page " + currentPage + " of " + totalPages);
    }

    public void displayMenu(final int menu) {
        this.displaySpacing();

        if (menus.containsKey(menu)) {
            this.menus.get(menu).show();
        }
    }

    public void displaySlots(final LocalDate date, final DateTimeFormatter dateFormatter,
                             final DateTimeFormatter hourFormatter, final List<Slot> slots) {
        this.displayLocalDate(date, dateFormatter);

        for (int i = 0; i < slots.size(); i++) {
            this.displaySlot(slots.get(i), hourFormatter, i);
        }
    }

    private void displaySlot(final Slot s, final DateTimeFormatter formatter, final int slotIndex) {
        System.out.print("Slot " + slotIndex + " -> ");
        this.displayHour(s.getStartTime(), formatter);
        System.out.print(" - ");
        this.displayHour(s.getEndTime(), formatter);

        if (s.getClass().equals(OpenSlot.class)) {
            System.out.println(": Available");
        } else if (s.getClass().equals(ClosedSlot.class)) {
            System.out.println(": Non Available");
        } else if (s.getClass().equals(BusySlot.class)) {
            System.out.print(": ");
            this.displayTask(((BusySlot)s).getTask());
        }
    }

    private void displayHour(final LocalTime localTime, final DateTimeFormatter formatter) {
        System.out.print(localTime.format(formatter));
    }

    private void displayTask(final Task task) {
        final StringBuilder sb = new StringBuilder("Title: " + task.getTitle() + " --- With: ");
        final List<String> people = task.getPeople();
        String delimiter = "";

        for (String p : people) {
            sb.append(delimiter);
            sb.append(p);
            delimiter = ",";
        }

        System.out.println(sb.toString());
    }

    private void displaySpacing() {
        System.out.println("\n".repeat(10));
    }
}