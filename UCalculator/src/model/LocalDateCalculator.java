package model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Stack;
import java.util.function.BiFunction;

class LocalDateCalculator {

    // State of operation results to backtrack
    private Stack<LocalDate> stack;

    LocalDateCalculator() {
        stack = new Stack<>();
    }

    void push(LocalDate localDate) {
        stack.push(localDate);
    }

    void push(BiFunction<LocalDate, Duration, LocalDate> biFunction, Duration duration) {
        stack.push(biFunction.apply(stack.peek(), duration));
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
}
