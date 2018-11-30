import controller.UCalculatorController;
import model.schedule.Schedule;
import model.UCalculatorModel;
import model.schedule.Task;
import view.UCalculatorView;

import java.time.LocalDate;
import java.util.Arrays;

public class UCalculatorApp {

    public static void main(String[] args) {

        Schedule schedule = new Schedule(60);
        schedule.addTask(LocalDate.now(), 20, 10, "atuaprima", Arrays.asList("diogo", "rafa"));
        //schedule.consult(LocalDate.now().plusDays(1)).forEach(System.out::println);
        //schedule.removeTask(LocalDate.now().plusDays(1), 1);

        //System.out.println(schedule.editTask(LocalDate.now(), 20, LocalDate.now(), 21, 2));

        //schedule.consult(LocalDate.now()).forEach(System.out::println);
        schedule.consult(LocalDate.now()).forEach(System.out::println);

//        schedule.editTask(LocalDate.now(), 21, LocalDate.now().plusDays(2), 1, 1);
//
//        System.out.println("#".repeat(20));
//        System.out.println("DATE " + LocalDate.now());
//        schedule.consult(LocalDate.now()).forEach(System.out::println);
//        System.out.println("#".repeat(20));
//        System.out.println("DATE " + LocalDate.now().plusDays(2));
//        schedule.consult(LocalDate.now().plusDays(2)).forEach(System.out::println);




        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
