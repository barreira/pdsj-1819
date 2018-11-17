package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Stack;
import java.util.function.BiFunction;

class LocalDateCalculator {

    private Stack<Result> stack;

    LocalDateCalculator() {
        stack = new Stack<>();
    }

    void add(BiFunction<Duration, Duration, Duration> biFunction, Duration duration) {
        Result previous =  stack.peek();
        Duration previousDuration = ((DurationResult) previous).get();

        stack.push(new Result(biFunction.apply(previousDuration, duration));
        }

        stack.push(new );
    }

    void push

    void popElement() {
        stack.pop();
    }

    Result calculate() {
        AST ast = new AST();

        while (!stack.empty()) {
            ast.add(stack.pop());
        }

        return ast.calculate();
    }
}
