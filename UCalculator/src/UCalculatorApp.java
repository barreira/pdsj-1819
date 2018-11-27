import controller.UCalculatorController;
import model.Schedule;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDateTime;

public class UCalculatorApp {

    public static void main(String[] args) {

        Schedule s = new Schedule();
        s.add("atuaprima", LocalDateTime.now(), LocalDateTime.now().plusDays(1));







        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
