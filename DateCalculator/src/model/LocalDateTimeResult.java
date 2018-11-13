package model;

import java.time.LocalDateTime;

class LocalDateTimeResult implements Result {

    private final LocalDateTime localDateTime;

    LocalDateTimeResult(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    LocalDateTime get() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return localDateTime.toString();
    }
}
