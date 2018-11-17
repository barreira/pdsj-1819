package model;

import java.time.*;
import java.util.function.BiFunction;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.temporal.ChronoUnit.DAYS;

public final class DateUtils {
    public static final BiFunction<LocalDateTime, Duration, LocalDateTime> datePlusDuration = LocalDateTime::plus;
    public static final BiFunction<Duration, Duration, Duration> durationPlusDuration = Duration::plus;
    public static final BiFunction<LocalDateTime, Duration, LocalDateTime> dateMinusDuration = LocalDateTime::minus;
    public static final BiFunction<Duration, Duration, Duration> durationMinusDuration = Duration::minus;
    public static final BiFunction<LocalDate, LocalDate, Period> intervalBetweenDates = Period::between;

    public static final BiFunction<LocalDateTime, Integer, LocalDateTime> datePlusWorkingDays = (x, y) -> {
        int count = 0;
        while(count < y) {
            DayOfWeek dow = x.getDayOfWeek();
            if(!(dow.equals(SATURDAY) || dow.equals(SUNDAY))){
                count++;
            }
            x = x.plusDays(1);
        }
        return x;
    };

    public static final BiFunction<LocalDateTime, Integer, LocalDateTime> datePlusFortnights = (x, y) -> x.plusDays(y * 14);
}
