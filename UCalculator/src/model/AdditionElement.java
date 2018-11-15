package model;

public class AdditionElement implements Element {

    private static final int PRIORITY = 0;

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
