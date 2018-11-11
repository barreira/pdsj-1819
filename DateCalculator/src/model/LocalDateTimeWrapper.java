package model;

import java.time.LocalDateTime;

class LocalDateTimeWrapper extends Result {

    private final LocalDateTime res;

    LocalDateTimeWrapper(LocalDateTime res) {
        this.res = res;
    }

    LocalDateTime get() {
        return res;
    }
}
