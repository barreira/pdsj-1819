package model;

import java.time.LocalDateTime;

public class LocalDateTimeElement implements Element {

    private static final int PRIORITY = 2;
    private final LocalDateTime localDateTime;

    public LocalDateTimeElement(final LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    LocalDateTime get() {
        return localDateTime;
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}
