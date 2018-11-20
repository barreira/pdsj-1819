package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.function.BiFunction;

public final class UCalculatorModel {

    private LocalDateCalculator localDateCalculator;

    public UCalculatorModel() {
        localDateCalculator = new LocalDateCalculator();
    }

    public void next(LocalDate LocalDate) {
        localDateCalculator.push(LocalDate);
    }

    public void next(BiFunction<LocalDate, Pair<Integer, TemporalUnit>, LocalDate> biFunction,
                     int duration, TemporalUnit temporalUnit) {
        localDateCalculator.push(biFunction, duration, temporalUnit);
    }

    public void next(BiFunction<LocalDate, Integer, LocalDate> biFunction, int argument) {
        localDateCalculator.push(biFunction, argument);
    }

    public void previous() {
        localDateCalculator.pop();
    }

    public LocalDate solve() {
        return localDateCalculator.peek();
    }

    public void clear() {
        localDateCalculator.clear();
    }
}
