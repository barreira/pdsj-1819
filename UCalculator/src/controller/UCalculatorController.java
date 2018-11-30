package controller;

import model.UCalculatorModel;
import model.config.Config;
import view.UCalculatorView;

import javax.swing.text.DateFormatter;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class UCalculatorController {
    private final DateTimeFormatter dateTimeFormat;
    private final DateTimeFormatter dateFormat;
    private final DateTimeFormatter timeFormat;

    private UCalculatorView view;
    private UCalculatorModel model;

    UCalculatorController() {
        dateTimeFormat = DateTimeFormatter.ofPattern(Config.getConfig().getProperty("DATE_TIME_PATTERN"));
        dateFormat = DateTimeFormatter.ofPattern(Config.getConfig().getProperty("DATE_PATTERN"));
        timeFormat = DateTimeFormatter.ofPattern(Config.getConfig().getProperty("TIME_PATTERN"));
    }

    public void setView(final UCalculatorView view) {
        this.view = view;
    }

    public void setModel(final UCalculatorModel model) {
        this.model = model;
    }

    public void startFlow() {
        String option;
        boolean displayMenu = true;

        do {
            if (displayMenu) {
                view.displayMenu(0);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    LocalCalculatorController localCalculatorController = new LocalCalculatorController();

                    localCalculatorController.setView(view);
                    localCalculatorController.setModel(model);
                    localCalculatorController.startFlow();

                    break;
                case "2":
                    TimeZoneController timeZoneController = new TimeZoneController();

                    timeZoneController.setView(view);
                    timeZoneController.setModel(model);
                    timeZoneController.startFlow();
                    break;
                case "3":
                    ScheduleController schedulerController = new ScheduleController();
                    schedulerController.setView(view);
                    schedulerController.setModel(model);
                    schedulerController.startFlow();

                    break;
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!\n");
                    displayMenu = false;
            }
        } while (!option.equals("0"));
    }
}