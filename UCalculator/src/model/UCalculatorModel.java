package model;

public final class UCalculatorModel {

    private LocalDateCalculator localDateCalculator;

    public UCalculatorModel() {
        localDateCalculator = new LocalDateCalculator();
    }

    public void addElement(Element e) {
        localDateCalculator.pushElement(e);
    }

    public void removeElement() {
        localDateCalculator.popElement();
    }

    public Result calculate() {
        return localDateCalculator.calculate();
    }
}
