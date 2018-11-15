package controller;

import jdk.swing.interop.SwingInterOpUtils;
import view.Menu;
import view.UCalculatorView;

public class UCalculatorController {

    private UCalculatorView view;

    public void setView(final UCalculatorView view) {
        this.view = view;
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
                        dateTimeCalculator();
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

    private void dateTimeCalculator() {
        System.out.println("DATE TIME CALCULATOR HERE");
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
