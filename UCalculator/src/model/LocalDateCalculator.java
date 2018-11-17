package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;
import java.util.function.BiFunction;

class LocalDateCalculator {

    // State of operation results to backtrack
    private Stack<LocalDateTime> stack;

    LocalDateCalculator() {
        stack = new Stack<>();
    }

    void push(LocalDateTime LocalDateTime) {
        stack.push(LocalDateTime);
    }

    void push(BiFunction<LocalDateTime, Duration, LocalDateTime> biFunction, Duration duration) {
        stack.push(biFunction.apply(stack.peek(), duration));
    }

    void push(BiFunction<LocalDateTime, Integer, LocalDateTime> biFunction, int argument) {
        stack.push(biFunction.apply(stack.peek(), argument));
    }

    LocalDateTime pop() {
        return stack.pop();
    }

    LocalDateTime peek() {
        return stack.peek();
    }
}
