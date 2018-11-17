package controller;

import model.*;
import view.Menu;
import view.UCalculatorView;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

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
        stack.clear();
        this.dateMenu();
    }


    private void dateMenu() {
        LocalDate localDate;

        System.out.println("Insert Date: ");
        localDate = Input.readDate(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        stack.push(localDate.toString());

        this.operationMenu();
    }


    private void durationMenu() {
        System.out.println("Insert Duration: ");
        int i = Input.readInt();
        Menu menu = view.getMenu(3);

        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        model.addElement(new PeriodElement(Period.ofDays(i)));
                        stack.push(i + " days");
                        break;
                    case "2":
                        model.addElement(new PeriodElement(Period.o));
                        stack.push("-");
                        break;
                    case "3":
                        model.addElement(new IntervalElement());
                        stack.push(",");
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


    private void operationMenu() {
        Menu menu = view.getMenu(2);

        if (menu != null) {
            String option;

            do {
                menu.show();
                option = Input.readString();

                switch (option) {
                    case "1":
                        model.addElement(new AdditionElement());
                        stack.push("+");
                        break;
                    case "2":
                        model.addElement(new SubtractionElement());
                        stack.push("-");
                        break;
                    case "3":
                        model.addElement(new IntervalElement());
                        stack.push(",");
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
