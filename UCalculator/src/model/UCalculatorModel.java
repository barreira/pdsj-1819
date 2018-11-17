package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.BiFunction;

public final class UCalculatorModel {

    private LocalDateTimeCalculator LocalDateTimeCalculator;

    public UCalculatorModel() {
        LocalDateTimeCalculator = new LocalDateTimeCalculator();
    }

    public void next(LocalDateTime LocalDateTime) {
        LocalDateTimeCalculator.push(LocalDateTime);
    }

    public void next(BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction, Duration duration) {
        LocalDateTimeCalculator.push(biFunction, duration);
    }

    public void next(BiFunction<LocalDateTime, Integer, LocalDateTime> biFunction, int argument) {
        LocalDateTimeCalculator.push(biFunction, argument);
    }


    public void previous() {
        LocalDateTimeCalculator.pop();
    }

    public LocalDateTime solve() {
        return LocalDateTimeCalculator.peek();
    }
}
