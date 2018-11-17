package model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.function.BiFunction;

import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.temporal.ChronoUnit.DAYS;

public final class UtilitaryDate {
    BiFunction<LocalDate, Duration, LocalDate> datePlusDuration = LocalDate::plus;
    BiFunction<Duration, Duration, Duration> durationPlusDuration = Duration::plus;
    BiFunction<LocalDate, Duration, LocalDate> dateMinusDuration = LocalDate::minus;
    BiFunction<Duration, Duration, Duration> durationMinusDuration = Duration::minus;
    BiFunction<LocalDate, LocalDate, Period> intervalBetweenDates = Period::between;

    BiFunction<LocalDate, Integer, LocalDate> datePlusWorkingDays = (x, y) -> {
        int count = 0;
        while(count < y) {
            DayOfWeek dow = x.getDayOfWeek();
            if(!(dow.equals(SATURDAY) || dow.equals(SUNDAY))){
                count++;
            }
            x = x.plus(1, DAYS);
        }
        return x;
    };

    BiFunction<LocalDate, Integer, LocalDate> datePlusFortnights = (x, y) -> x.plusDays(y * 14);
}
