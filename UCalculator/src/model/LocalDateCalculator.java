package model;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Stack;
import java.util.function.BiFunction;

class LocalDateCalculator {

    // State of operation results to backtrack
    private Stack<LocalDate> stack;

    LocalDateCalculator() {
        stack = new Stack<>();
    }

    void push(LocalDate LocalDate) {
        stack.push(LocalDate);
    }

    void push(BiFunction<LocalDate, Pair<Integer, TemporalUnit>, LocalDate> biFunction, int duration,
              TemporalUnit temporalUnit) {
        stack.push(biFunction.apply(stack.peek(), new Pair<>(duration, temporalUnit)));
    }

    void push(BiFunction<LocalDate, Integer, LocalDate> biFunction, int argument) {
        stack.push(biFunction.apply(stack.peek(), argument));
    }

    void pop() {
        stack.pop();
    }

    LocalDate peek() {
        return stack.peek();
    }

    void clear() {
        stack.clear();
    }
}
