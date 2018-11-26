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
        do {
            view.displayMenu(0);
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
                    //meetingSchedule();
                    break;
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0"));
    }
}