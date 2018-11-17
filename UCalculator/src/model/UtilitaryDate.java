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
    public static BiFunction<LocalDate, Duration, LocalDate> datePlusDuration = LocalDate::plus;
    public static BiFunction<Duration, Duration, Duration> durationPlusDuration = Duration::plus;
    public static BiFunction<LocalDate, Duration, LocalDate> dateMinusDuration = LocalDate::minus;
    public static BiFunction<Duration, Duration, Duration> durationMinusDuration = Duration::minus;
    public static BiFunction<LocalDate, LocalDate, Period> intervalBetweenDates = Period::between;

    public static BiFunction<LocalDate, Integer, LocalDate> datePlusWorkingDays = (x, y) -> {
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

    public static BiFunction<LocalDate, Integer, LocalDate> datePlusFortnights = (x, y) -> x.plusDays(y * 14);
}
