package model;

import java.time.Period;

public class PeriodElement implements Element {

    private static final int PRIORITY = 2;
    private final Period period;

    public PeriodElement(final Period period) {
        this.period = period;
    }

    public Period get() {
        return period;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
