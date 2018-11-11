package model;

import java.time.LocalDateTime;

public final class Facade {

    private LocalDateTimeCalculator localDateTimeCalculator;

    public Facade() {
         localDateTimeCalculator = new LocalDateTimeCalculator();
    }

    // TODO: tem que se mandar uma exception
    public LocalDateTime executeLocal(final String command) {
        return localDateTimeCalculator.execute(command);
    }
}
