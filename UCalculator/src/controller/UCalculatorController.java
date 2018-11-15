package controller;

import view.Menu;
import view.UCalculatorView;

import java.util.Arrays;

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
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            } while (!option.equals("0"));
        }
    }
}
