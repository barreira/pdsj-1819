package controller;

import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class ConfigController {
    private UCalculatorView view;
    private UCalculatorModel model;

    void setView(UCalculatorView view) {
        this.view = view;
    }

    void setModel(UCalculatorModel model) {
        this.model = model;
    }

    void startFlow() {
        String option;
        do {
            view.displayMenu(12);
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    editDateTimePattern();
                    stopExecution();
                    break;
                case "2":
                    editDatePattern();
                    stopExecution();
                    break;
                case "3":
                    editTimePattern();
                    stopExecution();
                    break;
                case "4":
                    editSlotSize();
                    stopExecution();
                    break;
                case "5":
                    editStartTime();
                    stopExecution();
                    break;
                case "6":
                    editEndTime();
                    stopExecution();
                    break;
                case "7":
                    setDefault();
                    stopExecution();
                    break;
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!\n");
                    break;
            }
        } while (!option.equals("0"));
    }

    private void editDateTimePattern() {
        view.displayMessage("Old date time pattern is " + model.getDateTimePattern() + "\n");
        view.displayMessage("Insert new pattern: ");
        String pattern = Input.readString();
        if (model.setDateTimePattern(pattern)) {
            view.displayMessage("Success setting the new date time pattern.\n");
        } else {
            view.displayMessage("An error occurred setting the new date time pattern.\n");
        }
    }

    private void editDatePattern() {
        view.displayMessage("Old date pattern is " + model.getDatePattern() + "\n");
        view.displayMessage("Insert new pattern: ");
        String pattern = Input.readString();
        if (model.setDatePattern(pattern)) {
            view.displayMessage("Success setting the new date pattern.\n");
        } else {
            view.displayMessage("An error occurred setting the new date pattern.\n");
        }
    }

    private void editTimePattern() {
        view.displayMessage("Old time pattern is " + model.getTimePattern() + "\n");
        view.displayMessage("Insert new pattern: ");
        String pattern = Input.readString();
        if (model.setTimePattern(pattern)) {
            view.displayMessage("Success setting the new time pattern.\n");
        } else {
            view.displayMessage("An error occurred setting the new time pattern.\n");
        }
    }

    private void editSlotSize() {
        view.displayMessage("This change resets the Schedule. Do you still want to proceed? Y/n\n");
        if (Input.readString().equals("Y")) {
            view.displayMessage("Old slot duration is " + model.getSlotSize() + "\n");
            view.displayMessage("Insert new slot duration (m): ");
            int slotSize = Input.readInt();

            if (model.setSlotSize(slotSize)) {
                view.displayMessage("Success. You need to restart the app for changes to take effect.\n");
            } else {
                view.displayMessage("An error occurred setting the slot duration.\n");
            }
        } else {
            view.displayMessage("Operation canceled.");
        }
    }

    private void editStartTime() {
        view.displayMessage("Old start time is " + model.getStartTime().toString() + "\n");
        view.displayMessage("Insert new start time (" + model.getTimePattern() + "): ");
        LocalTime time = Input.readTime(DateTimeFormatter.ofPattern(model.getTimePattern()));

        if (model.setStartSlotTime(time)) {
            view.displayMessage("Success setting the start time.\n");
        } else {
            view.displayMessage("An error occurred setting the start time.\n");
        }
    }

    private void editEndTime() {
        view.displayMessage("Old end time is " + model.getEndTime().toString() + "\n");
        view.displayMessage("Insert new end time (" + model.getTimePattern() + "): ");
        LocalTime time = Input.readTime(DateTimeFormatter.ofPattern(model.getTimePattern()));

        if (model.setEndSlotTime(time)) {
            view.displayMessage("Success setting the end time.\n");
        } else {
            view.displayMessage("An error occurred setting the end time.\n");
        }
    }

    private void setDefault() {
        model.setDefault();
        view.displayMessage("The default configuration was set.\n");

    }

    private void stopExecution() {
        view.displayMessage("Press ENTER to continue... ");
        Input.readString();
    }
}
