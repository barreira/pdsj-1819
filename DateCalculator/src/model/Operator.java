package model;

public class Operator extends Operation {

    private String op;
    private Operation x;
    private Operation y;

    @Override
    Result execute() {
        Result resX = x.execute();
        Result resY = y.execute();

        switch (op) {
            case "+":
                if (resX instanceof LocalDateTimeWrapper) {

                }
                break;
        }

        return null;
    }
}
