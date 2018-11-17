package model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.function.BiFunction;

public final class UCalculatorModel {

    private LocalDateCalculator localDateCalculator;

    public UCalculatorModel() {
        localDateCalculator = new LocalDateCalculator();
    }

    public void next(LocalDate localDate) {
        localDateCalculator.push(localDate);
    }

    public void next(BiFunction<LocalDate, Duration, LocalDate> biFunction, Duration duration) {
        localDateCalculator.push(biFunction, duration);
    }

    public void next(BiFunction<LocalDate, Integer, LocalDate> biFunction, int argument) {
        localDateCalculator.push(biFunction, argument);
    }


    public void previous() {
        localDateCalculator.pop();
    }

    public LocalDate compute() {
        return localDateCalculator.peek();
    }
}
