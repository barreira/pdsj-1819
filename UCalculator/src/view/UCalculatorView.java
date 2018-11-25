package view;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public final class UCalculatorView {

    private Map<Integer, Menu> menus;

    public UCalculatorView() {
        menus = Map.ofEntries(
                entry(0, new Menu("MAIN MENU", Arrays.asList(
                        new Option("Local Date Time Calculator ----", "1"),
                        new Option("Timezone Date Time Calculator -", "2"),
                        new Option("Meeting Schedule --------------", "3"),
                        new Option("End Session -------------------", "0")))),
                entry(1, new Menu("LOCAL DATE TIME CALCULATOR", Arrays.asList(
                        new Option("Date Calculator -----", "1"),
                        new Option("Interval Calculator -", "2"),
                        new Option("Weeks Calculator ----", "3"),
                        new Option("Back ----------------", "0")))),
                entry(2, new Menu("SELECT OPERATION", Arrays.asList(
                        new Option("Add ------", "1"),
                        new Option("Subtract -", "2"),
                        new Option("Solve ----", "3"),
                        new Option("Back -----", "4"),
                        new Option("Cancel ---", "0")))),
                entry(3, new Menu("SELECT DURATION UNITS", Arrays.asList(
                        new Option("Days ---------", "1"),
                        new Option("Working Days -", "2"),
                        new Option("Weeks --------", "3"),
                        new Option("Fortnights ---", "4"),
                        new Option("Months -------", "5"),
                        new Option("Years --------", "6"),
                        new Option("Back ---------", "0")))),
                entry(4, new Menu("SELECT RESULT UNITS", Arrays.asList(
                        new Option("Days ---------", "1"),
                        new Option("Working Days -", "2"),
                        new Option("Weeks --------", "3"),
                        new Option("Fortnights ---", "4"),
                        new Option("Months -------", "5"),
                        new Option("Years --------", "6"),
                        new Option("Period -------", "7"),
                        new Option("Cancel -------", "0")))),
                entry(5, new Menu("Weeks Calculator", Arrays.asList(
                        new Option("Week number of Date -", "1"),
                        new Option("Date of Week number -", "2"),
                        new Option("Back ----------------", "0")))),
                entry(6, new Menu("Timezone Calculator", Arrays.asList(
                        new Option("Timezone Converter -", "1"),
                        new Option("Travel Calculator --", "2"),
                        new Option("Back ---------------", "0"))))
        );
    }

    public Menu getMenu(final int key) {
        return menus.get(key);
    }


    public void displayMessage(final String message) {
        System.out.print(message);
    }


    public void displaySpacing() {
        System.out.println("\n".repeat(10));
    }


    public void displayLocalDate(final LocalDate date, final DateTimeFormatter formatter) {
        System.out.println(date.format(formatter));
    }

    public void displayPeriod(final Period period) {
        System.out.println(period.getYears() + " years, " + period.getMonths() + " months, and " +
                period.getDays() + " days");
    }

    public void displayPage(List<String> elements, int currentPage, int totalPages) {
        int i = 1;
        for(String element : elements) {
            // TODO format output strings
            System.out.println(element + " " + i);
            i++;
        }
        System.out.println("Page " + currentPage + " of " + totalPages);
        System.out.println("Previous - p");
        System.out.println("Next     - n");
        System.out.println("Cancel   - 0");


    }
}
