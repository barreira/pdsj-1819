package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;
import java.util.function.BiFunction;

class LocalDateTimeCalculator {

    // State of operation results to backtrack
    private Stack<LocalDateTime> stack;

    LocalDateTimeCalculator() {
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

    void pop() {
        stack.pop();
    }

    LocalDateTime peek() {
        return stack.peek();
    }
}
