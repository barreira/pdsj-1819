package model;

import java.time.Duration;

public class DurationResult implements Result {
    private Duration duration;

    public DurationResult(Duration duration) {
        this.duration = duration;
    }

    public Duration get() {
        return duration;
    }
}
