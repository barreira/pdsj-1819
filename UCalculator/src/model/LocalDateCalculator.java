package model;

import java.util.Stack;

class LocalDateCalculator {

    private Stack<Element> stack;

    LocalDateCalculator() {
        stack = new Stack<>();
    }

    void pushElement(Element e) {
        stack.push(e);
    }

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
