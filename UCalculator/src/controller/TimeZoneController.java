package controller;

import model.UCalculatorModel;
import view.Menu;
import view.UCalculatorView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

class TimeZoneController {

    private UCalculatorView view;
    private UCalculatorModel model;

    void setView(UCalculatorView view) {
        this.view = view;
    }

    void setModel(UCalculatorModel model) {
        this.model = model;
    }

    void startFlow() {
        model.initTimeZoneIDs();

        String option;
        do {
            view.displayMenu(6);
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    timezoneConverter();
                    break;
                case "2":
                    travelCalculator();
                    break;
                case "0":
                    break;
                default:
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("0"));
    }

    private void timezoneConverter() {
        view.displayMessage("Enter a location (0 to cancel): ");
        final String location = Input.readString();

        if(!location.equals("0")) {
            final List<String> ids = model.getMatchedTimezoneIDs(location);

            if(ids.size() > 1) {
                Paging paging = new Paging(5, ids);
                int flag = 0;
                String option;

                do {
                    if (flag == 0) {
                        view.displayPage(paging.nextPage(), paging.getCurrentPage(), paging.getTotalPages());
                    } else if (flag == 1){
                        view.displayPage(paging.previousPage(), paging.getCurrentPage(), paging.getTotalPages());
                    } else {
                        view.displayPage(paging.currentPage(), paging.getCurrentPage(), paging.getTotalPages());
                    }

                    view.displayMessage("Previous - p\n");
                    view.displayMessage("Next ----- n\n");
                    view.displayMessage("Cancel --- 0\n");
                    view.displayMessage("Insert option: ");
                    option = Input.readString();

                    try {
                        int op = Integer.parseInt(option);
                        String s = paging.get(op - 1);

                        if (s.equals("")) {
                            if (op != 0) {
                                view.displayMessage("Invalid option!\n");
                            }
                        } else {
                            LocalDateTime result = model.getTimeZone(s, LocalDateTime.now());
                            view.displayMessage("Current timezone at " + s + " is " + result.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ".\n");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        switch (option) {
                            case "n":
                                flag = 0;
                                break;
                            case "p":
                                flag = 1;
                                break;
                            default:
                                flag = 2;
                                view.displayMessage("Invalid option!\n");
                                break;
                        }
                    }
                } while (!option.equals("0"));
            } else if (ids.size() == 0){
                view.displayMessage("Location not found!\n");
            } else {
                view.displayMessage(
                        "Current timezone at " + ids.get(0) + " is " +
                                model.getTimeZone(ids.get(0), LocalDateTime.now())
                                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ".\n");

            }
        }
    }

    // TODO jo�o tenta acabar esta fun��o abarcando escalas
    private void travelCalculator() {
        view.displayMessage("Enter your destination (0 to exit): ");
        final String location = Input.readString();

        if(!location.equals("0")) {
            final List<String> ids = model.getMatchedTimezoneIDs(location);

            if(ids.size() > 1) {
                Paging paging = new Paging(5, ids);
                boolean next = true;
                String option;

                do {
                    if (next) {
                        view.displayPage(paging.nextPage(), paging.getCurrentPage(), paging.getTotalPages());
                    } else {
                        view.displayPage(paging.previousPage(), paging.getCurrentPage(), paging.getTotalPages());
                    }

                    view.displayMessage("Previous - p\n");
                    view.displayMessage("Next ----- n\n");
                    view.displayMessage("Cancel --- 0\n");
                    view.displayMessage("Insert option: ");
                    option = Input.readString();

                    try {
                        int op = Integer.parseInt(option);
                        String s = paging.get(op - 1);

                        if (s.equals("")) {
                            if (op != 0) {
                                view.displayMessage("Invalid option!\n");
                            }
                        } else {
                            view.displayMessage("Insert departure datetime (dd/MM/yyyy HH:mm): ");
                            LocalDateTime localDateTime = Input.readDateTime(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                            view.displayMessage("Insert departure datetime (HH:mm): ");
                            LocalTime localTime = Input.readTime(DateTimeFormatter.ofPattern("HH:mm"));

                            view.displayMessage(model.getArrivalTime(s, localDateTime, localTime).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                            break;
                        }
                        //TODO complete
                    } catch (NumberFormatException e) {
                        switch (option) {
                            case "n":
                                next = true;
                                break;
                            case "p":
                                next = false;
                                break;
                            default:
                                view.displayMessage("Invalid option!\n");
                                break;
                        }
                    }
                } while (!option.equals("0"));
            } else if (ids.size() == 0){
                view.displayMessage("Location not found!\n");
            } else {
                view.displayMessage(
                        "Current timezone at " + ids.get(0) + " is " +
                                model.getTimeZone(ids.get(0), LocalDateTime.now())
                                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ".\n");

            }
        }
    }
}
