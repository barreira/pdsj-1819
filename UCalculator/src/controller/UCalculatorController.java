package controller;

import model.UCalculatorModel;
import view.UCalculatorView;

public class UCalculatorController {
    private UCalculatorView view;
    private UCalculatorModel model;

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
                case "4":
                    ConfigController configController = new ConfigController();
                    configController.setView(view);
                    configController.setModel(model);
                    configController.startFlow();
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