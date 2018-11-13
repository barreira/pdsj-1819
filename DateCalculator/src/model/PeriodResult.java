package model;

import java.time.Period;

public class PeriodResult implements Result {

    private final Period period;

    PeriodResult(Period period) {
        this.period = period;
    }

    Period get() {
        return period;
    }


    @Override
    public String toString() {
        return period.toString();
    }
}
