import controller.UCalculatorController;
import model.Schedule;
import model.UCalculatorModel;
import view.UCalculatorView;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UCalculatorApp {

    public static void main(String[] args) {

        Schedule s = new Schedule();
        System.out.println(s.add("atuaprimade4", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
        System.out.println(s.add("atuaprimade3", LocalDateTime.now().plusHours(3), LocalDateTime.now().plusDays(1)));
        System.out.println(s.add("atuaprimaboua", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3)));


        s.editDescription(1, "atuaprimaveiose");
        s.editDescription(3, "tambemseveio");
        System.out.println(s.consultByDate(LocalDate.now()));




        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
