import controller.UCalculatorController;
import model.Schedule;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalTime;

public class UCalculatorApp {

    public static void main(String[] args) {
        Schedule schedule = new Schedule(60);
        System.out.println(schedule.setStartSlot(LocalTime.of(8, 30)));
        //bug here
        System.out.println(schedule.setEndSlot(LocalTime.of(19, 30)));
        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
