package controller;

import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
        boolean displayMenu = true;

        do {
            if (displayMenu) {
                view.displayMenu(8);
            }

            displayMenu = true;
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
                    displayMenu = false;
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
                int pagingType = 2;
                boolean displayPage = true;
                String option;

                do {
                    if (displayPage) {
                        this.managePaging(paging, pagingType);
                    }

                    view.displayMessage("Insert option: ");
                    displayPage = true;
                    option = Input.readString();

                    try {
                        int op = Integer.parseInt(option);
                        String s = paging.getElement(op - 1);

                        if (s != null) {
                            if (s.equals("")) {
                                if (op != 0) {
                                    view.displayMessage("Invalid option!\n");
                                    view.displayMessage("Insert option: ");
                                    displayPage = false;
                                }
                            } else {
                                LocalDateTime result = model.getTimeZone(s, LocalDateTime.now());
                                view.displayMessage("Current timezone at " + s + " is " +
                                        result.format(DateTimeFormatter.ofPattern(model.getDateTimePattern())) + ".\n");
                                this.stopExecution();
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        pagingType = this.getPagingType(option);

                        if (pagingType == 2) {
                            displayPage = false;
                        }
                    }
                } while (!option.equals("0"));
            } else if (ids.size() == 0){
                view.displayMessage("Location not found!\n");
                view.displayMessage("Press enter to continue: ");
                Input.readString();
            } else {
                view.displayMessage(
                        "Current timezone at " + ids.get(0) + " is " +
                                model.getTimeZone(ids.get(0), LocalDateTime.now())
                                        .format(DateTimeFormatter.ofPattern(model.getDateTimePattern())) + ".\n");
                this.stopExecution();
            }
        }
    }

    private void travelCalculator() {
        Map<String, SimpleEntry<LocalTime, LocalTime>> connections = new LinkedHashMap<>();
        LocalDateTime end, endLocal;
        String option;
        boolean displayMenu = true;

        view.displayMessage("Insert departure datetime (" + model.getDateTimePattern() + "): ");
        LocalDateTime start = Input.readDateTime(DateTimeFormatter.ofPattern(model.getDateTimePattern()));

        do {
            if (displayMenu) {
                view.displayMenu(9);
            }

            displayMenu = true;
            view.displayMessage("Insert option: ");
            option = Input.readString();

            switch (option) {
                case "1":
                    this.addConnection(connections);
                    break;
                case "2":
                    if (connections.size() == 0) {
                        view.displayMessage("You must insert at least one connection!\n");
                        this.stopExecution();
                    }

                    break;
                case "0":
                    return;
                default:
                    displayMenu = false;
                    view.displayMessage("Invalid option!\n");
            }
        } while (!option.equals("2"));

        for (Entry<String, SimpleEntry<LocalTime, LocalTime>> c : connections.entrySet()) {
            LocalTime duration = c.getValue().getKey();
            LocalTime timeBetween = c.getValue().getValue();
            final SimpleEntry<LocalDateTime, LocalDateTime> travelTimes = model.getTravelDateTimes(start, timeBetween, duration);

            start = travelTimes.getKey();
            end = travelTimes.getValue();
            endLocal = model.getArrivalTime(c.getKey(), start, duration);

            view.displayMessage("Arrival at " + c.getKey() + " at ");
            view.displayLocalDateTime(end, DateTimeFormatter.ofPattern(model.getDateTimePattern()));
            view.displayMessage(" (");
            view.displayLocalDateTime(endLocal, DateTimeFormatter.ofPattern(model.getDateTimePattern()));
            view.displayMessage(" local)\n");

            start = end;
        }

        if (connections.size() > 0) {
            this.stopExecution();
        }
    }

    private void addConnection(Map<String, SimpleEntry<LocalTime, LocalTime>> connections) {
        view.displayMessage("Enter connection location (0 to cancel): ");
        final String location = Input.readString();

        if (!location.equals("0")) {
            final List<String> ids = model.getMatchedTimezoneIDs(location);

            if (ids.size() > 1) {
                Paging paging = new Paging(ids, 5);
                int pagingType = 2;
                boolean displayPage = true;
                String option;

                do {
                    if (displayPage) {
                        this.managePaging(paging, pagingType);
                    }

                    view.displayMessage("Insert option: ");
                    displayPage = true;
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
                                view.displayMessage("Insert flight duration (" + model.getTimePattern() + "): ");
                                LocalTime duration = Input.readTime(DateTimeFormatter.ofPattern("" + model.getTimePattern() + ""));
                                LocalTime between = LocalTime.of(0, 0);

                                if (connections.size() != 0) {
                                    view.displayMessage("Insert time between flights (" + model.getTimePattern() + "): ");
                                    between = Input.readTime(DateTimeFormatter.ofPattern(model.getTimePattern()));
                                }

                                SimpleEntry res = connections.put(s, new SimpleEntry<>(duration, between));

                                if (res != null) { // já há entry com key igual
                                    view.displayMessage("Invalid location: already has a connection associated");
                                    this.stopExecution();
                                }

                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        pagingType = this.getPagingType(option);

                        if (pagingType == 2) {
                            displayPage = false;
                        }
                    }
                } while (!option.equals("0"));
            } else if (ids.size() == 0){
                view.displayMessage("Location not found!\n");
            } else {
                view.displayMessage("Insert flight duration (" + model.getTimePattern() + "): ");
                LocalTime duration = Input.readTime(DateTimeFormatter.ofPattern(model.getTimePattern()));
                LocalTime between = LocalTime.of(0, 0);

                if (connections.size() != 0) {
                    view.displayMessage("Insert time between flights (" + model.getTimePattern() + "): ");
                    between = Input.readTime(DateTimeFormatter.ofPattern(model.getTimePattern()));
                }

                SimpleEntry res = connections.put(ids.get(0), new SimpleEntry<>(duration, between));

                if (res != null) {
                    view.displayMessage("Invalid location: already has a connection associated");
                    this.stopExecution();
                }
            }
        }
    }
    
    private void managePaging(final Paging paging, final int pagingType) {
        if (pagingType == 0) {
            view.displayPage(paging.nextPage(), paging.getCurrentPage(), paging.getTotalPages());
        } else if (pagingType == 1){
            view.displayPage(paging.previousPage(), paging.getCurrentPage(), paging.getTotalPages());
        } else {
            view.displayPage(paging.currentPage(), paging.getCurrentPage(), paging.getTotalPages());
        }

        view.displayMessage("Previous - p\n");
        view.displayMessage("Next ----- n\n");
        view.displayMessage("Cancel --- 0\n");
    }

    private int getPagingType(final String option) {
        int pagingType;

        switch (option) {
            case "n":
                pagingType = 0;
                break;
            case "p":
                pagingType = 1;
                break;
            default:
                pagingType = 2;
                view.displayMessage("Invalid option!\n");
                break;
        }

        return pagingType;
    }

    private void stopExecution() {
        view.displayMessage("Press ENTER to continue... ");
        Input.readString();
    }
}