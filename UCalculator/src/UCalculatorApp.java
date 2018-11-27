import controller.UCalculatorController;
import model.Schedule;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UCalculatorApp {

    public static void main(String[] args) {

        Schedule s = new Schedule();
        // s.add("atuaprimade4", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        // s.add("atuaprimade3", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusDays(1));
        s.add("control", LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 1, 0));
        s.add("control", LocalDateTime.of(2000, 1, 1, 1, 0), LocalDateTime.of(2000, 1, 1, 2, 0));


        s.edit(2, LocalDateTime.of(2000, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 1, 0));
        System.out.println(s.consult(1));




        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
