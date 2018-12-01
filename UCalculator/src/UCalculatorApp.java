import controller.UCalculatorController;
import model.UCalculatorModel;
import model.Config;
import view.UCalculatorView;

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
