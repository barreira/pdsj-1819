import controller.UCalculatorController;
import model.Schedule;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDate;
import java.util.Arrays;

public class UCalculatorApp {

    public static void main(String[] args) {

        Schedule schedule = new Schedule(60);
        schedule.fillSlot(21, LocalDate.now(), "atuaprima", Arrays.asList("diogo", "rafa"), 2);
        // schedule.fillSlot(23, LocalDate.now(), "atuaprima2", Arrays.asList("diogo", "rafa"), 2);

        schedule.edit(21, LocalDate.now(), 22, LocalDate.now().plusDays(2));

        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
