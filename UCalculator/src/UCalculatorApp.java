import controller.UCalculatorController;
import view.UCalculatorView;

public class UCalculatorApp {

    public static void main(String[] args) {
        UCalculatorView view = new UCalculatorView();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.startFlow();
    }
}
