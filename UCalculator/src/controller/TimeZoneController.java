package controller;

import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
            view.displayMenu(8);
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
                Paging paging = new Paging(ids, 5);
                int flag = 2;
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
                        String s = paging.getElement(op - 1);

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

    private void travelCalculator() {
        Map<String, LocalTime> connections = new LinkedHashMap<>();
        LocalDateTime end, endLocal;
        String option;

        view.displayMessage("Insert departure datetime (dd/MM/yyyy HH:mm): ");
        LocalDateTime start = Input.readDateTime(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        do {
            view.displayMenu(19);
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    addConnection(connections);
                    break;
                case "2":
                    if (connections.size() == 0) {
                        view.displayMessage("You must insert at least one connection!\n");
                    }
                    break;
                case "0":
                    return;
                default:
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("2"));

        for (Map.Entry<String, LocalTime> c : connections.entrySet()) {
            end = start.plusHours(c.getValue().getHour()).plusMinutes(c.getValue().getMinute());
            endLocal = model.getArrivalTime(c.getKey(), start, c.getValue());

            view.displayMessage("Arrival at " + c.getKey() + " at ");
            view.displayLocalDateTime(end, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            view.displayMessage(" (");
            view.displayLocalDateTime(endLocal, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            view.displayMessage(" local)\n");

            start = end;
        }
    }

    private void addConnection(Map<String, LocalTime> connections) {
        view.displayMessage("Enter connection location (0 to cancel): ");
        final String location = Input.readString();

        if (!location.equals("0")) {
            final List<String> ids = model.getMatchedTimezoneIDs(location);

            if (ids.size() > 1) {
                Paging paging = new Paging(ids, 5);
                int flag = 2;
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
                    String s;

                    try {
                        int op = Integer.parseInt(option);
                        s = paging.getElement(op - 1);

                        if (s != null) {
                            if (s.equals("")) {
                                if (op != 0) {
                                    view.displayMessage("Invalid option!\n");
                                }
                            } else {
                                view.displayMessage("Insert flight duration (HH:mm): ");
                                LocalTime duration = Input.readTime(DateTimeFormatter.ofPattern("HH:mm"));

                                LocalTime res = connections.put(s, duration);

                                if (res != null) {
                                    view.displayMessage("Invalid location: already has a connection associated");
                                }
                                break;
                            }
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
                view.displayMessage("Insert flight duration (HH:mm): ");
                LocalTime duration = Input.readTime(DateTimeFormatter.ofPattern("HH:mm"));

                LocalTime res = connections.put(ids.get(0), duration);

                if (res != null) {
                    view.displayMessage("Invalid location: already has a connection associated");
                }
            }
        }
    }
}
