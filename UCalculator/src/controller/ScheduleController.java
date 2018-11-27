package controller;

import model.UCalculatorModel;
import view.UCalculatorView;

public class ScheduleController {
    private UCalculatorView view;
    private UCalculatorModel model;

    void setView(UCalculatorView view) {
        this.view = view;
    }

    void setModel(UCalculatorModel model) {
        this.model = model;
    }

    void startFlow() {
        view.displayMenu(7);
    }
}
