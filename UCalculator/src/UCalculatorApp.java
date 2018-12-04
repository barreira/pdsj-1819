import controller.UCalculatorController;
import model.Schedule;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalTime;

public class UCalculatorApp {

    public static void main(String[] args) {
        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
