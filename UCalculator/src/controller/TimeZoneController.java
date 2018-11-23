package controller;

import model.UCalculatorModel;
import view.Menu;
import view.UCalculatorView;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
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
        Menu menu = view.getMenu(6);

        model.initTimeZoneIDs();

        if (menu != null) {
            String option;
            do {
                menu.show();
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
    }

    private void timezoneConverter() {
        view.displayMessage("Insert a date (dd/MM/yyyy HH:mm): ");
        final LocalDateTime localDateTime = Input.readDateTime(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        boolean isLocal = true;
        boolean exit = false;
        final Menu menu = view.getMenu(7);


        /*if (menu != null) {
            String option;
            do {
                menu.show();
                view.displayMessage("Insert option: ");
                option = Input.readString();

                switch (option) {
                    case "1":
                        isLocal = true;
                        exit = true;
                        break;
                    case "2":
                        isLocal = false;
                        exit = true;
                        break;
                    case "0":
                        break;
                    default:
                        view.displayMessage("Invalid option!\n");
                }
            } while (!option.equals("0") && !exit);
        }*/

        view.displayMessage("Enter a Location: ");
        final String location = Input.readString();
        final List<String> ids = model.getMatchedTimezoneIDs(location);
        final ZoneId zoneId = ZoneId.of(ids.get(0));
        final ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        //localDateTime.plus(zonedDateTime.getOffset())

        //System.out.println(zonedDateTime.getOffset().getTotalSeconds());
        System.out.println(localDateTime.plusSeconds(zonedDateTime.getOffset().getTotalSeconds()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
    }

    private void travelCalculator() {

    }
}
