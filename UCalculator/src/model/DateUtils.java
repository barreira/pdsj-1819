package model;

import java.time.*;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    public static int weekNumberOfLocalDate(LocalDateTime localDateTime) {
        return localDateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    public static Pair<LocalDateTime, LocalDateTime> localDateOfYearWeekNumber(int weekNumber, Year year) {
        LocalDate firstMonday = year.atDay(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate start = firstMonday.plusWeeks(weekNumber - 1);
        LocalDate end = start.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        return new Pair<>(start.atTime(0, 0), end.atTime(0, 0));
    }
}
