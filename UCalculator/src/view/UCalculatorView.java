package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                entry(6, new Menu("\t*** SELECT DAY OF THE WEEK ***", Arrays.asList(
                        new Option("Monday ----", "1"),
                        new Option("Tuesday ---", "2"),
                        new Option("Wednesday -", "3"),
                        new Option("Thursday --", "4"),
                        new Option("Friday ----", "5"),
                        new Option("Saturday --", "6"),
                        new Option("Sunday ----", "7"),
                        new Option("Cancel ----", "0")))),
                entry(7, new Menu("SELECT PLACE", Arrays.asList(
                        new Option("First --", "1"),
                        new Option("Second -", "2"),
                        new Option("Third --", "3"),
                        new Option("Fourth -", "4"),
                        new Option("Fifth --", "5"),
                        new Option("All ----", "6"),
                        new Option("Cancel -", "0")))),
                entry(8, new Menu("Timezone Calculator", Arrays.asList(
                        new Option("Timezone Converter -", "1"),
                        new Option("Travel Calculator --", "2"),
                        new Option("Back ---------------", "0")))),
                entry(19, new Menu("ADD CONNECTION", Arrays.asList(
                        new Option("Add Connection --------------", "1"),
                        new Option("Calculate Total Travel Time -", "2"),
                        new Option("Cancel ----------------------", "0")))),
                entry(9, new Menu("SCHEDULER MANAGER", Arrays.asList(
                        new Option("Add -----", "1"),
                        new Option("Edit ----", "2"),
                        new Option("Consult -", "3"),
                        new Option("Remove --", "4"),
                        new Option("Back ----", "0"))))
        );
    }

    public void displayMessage(final String message) {
        System.out.print(message);
    }

    public void displayMessage(final String message, final boolean withSpacing) {
        if (withSpacing) {
            this.displaySpacing();
        }

        System.out.println(message);
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
            // TODO format output strings
            System.out.println(element + " " + i);
            i++;
        }
        System.out.println("Page " + currentPage + " of " + totalPages);
    }

    public void displayPage(final List<String> elements, final int currentPage, final int totalPages, boolean enumerate) {
        this.displaySpacing();
        int i = 1;
        for(String element : elements) {
            System.out.print(element);
            if (enumerate) {
                System.out.print(" " + i);
            }
            System.out.println();
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

    private void displaySpacing() {
        System.out.println("\n".repeat(10));
    }
}
