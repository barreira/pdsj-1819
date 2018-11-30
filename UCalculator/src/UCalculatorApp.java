import controller.UCalculatorController;
import model.UCalculatorModel;
import model.config.Config;
import view.UCalculatorView;

public class UCalculatorApp {

    public static void main(String[] args) {
        Config config = Config.getInstance();

        UCalculatorView view = new UCalculatorView();
        UCalculatorModel model = new UCalculatorModel();
        UCalculatorController controller = new UCalculatorController();

        controller.setView(view);
        controller.setModel(model);
        controller.startFlow();
    }
}
