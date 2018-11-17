package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;

public final class UCalculatorModel {

    private LocalDateCalculator localDateCalculator;

    public UCalculatorModel() {
        localDateCalculator = new LocalDateCalculator();
    }

    public void next(LocalDateTime LocalDateTime) {
        localDateCalculator.push(LocalDateTime);
    }

    public void next(BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction, Duration duration) {
        localDateCalculator.push(biFunction, duration);
    }

    public void next(BiFunction<LocalDateTime, Integer, LocalDateTime> biFunction, int argument) {
        localDateCalculator.push(biFunction, argument);
    }

    public void previous() {
        localDateCalculator.pop();
    }

    public LocalDateTime solve() {
        return localDateCalculator.peek();
    }
}
