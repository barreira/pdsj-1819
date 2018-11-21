package model;

import java.time.*;
import java.time.temporal.*;
import java.util.function.BiFunction;

import static java.time.DayOfWeek.*;

final class DateUtils {

    static final BiFunction<LocalDate, Pair<Integer, TemporalUnit>, LocalDate> datePlusDuration =
            (x, y) -> x.isSupported(y.getSecond()) ? x.plus(y.getFirst(), y.getSecond()) : x;

    static final BiFunction<LocalDate, Pair<Integer, TemporalUnit>, LocalDate> dateMinusDuration =
            (x, y) -> x.isSupported(y.getSecond()) ? x.minus(y.getFirst(), y.getSecond()) : x;

    static final BiFunction<LocalDate, Integer, LocalDate> datePlusWorkingDays = (x, y) -> {
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

    static final BiFunction<LocalDate, Integer, LocalDate> dateMinusWorkingDays = (x, y) -> {
        int count = 0;
        while(count < y) {
            DayOfWeek dow = x.getDayOfWeek();
            if(!(dow.equals(SATURDAY) || dow.equals(SUNDAY))){
                count++;
            }
            x = x.minusDays(1);
        }
        return x;
    };

    static final BiFunction<LocalDate, Integer, LocalDate> datePlusFortnights =
            (x, y) -> x.plusDays(y * 14);

    static final BiFunction<LocalDate, Integer, LocalDate> dateMinusFortnights =
            (x, y) -> x.minusDays(y * 14);

    static final BiFunction<LocalDate, LocalDate, Period> intervalBetweenDates = Period::between;

    static long intervalInUnit(LocalDate first, LocalDate second, ChronoUnit chronoUnit) {
        try {
            return first.until(second, chronoUnit);
        } catch (Exception e) {
            return -1;
        }
    }

    static long intervalInFortnights(LocalDate first, LocalDate second) {
        try {
            return first.until(second, ChronoUnit.WEEKS) / 2;
        } catch (Exception e) {
            return -1;
        }
    }

    static long intervalInWorkingDays(LocalDate first, LocalDate second) {
        if (first.getDayOfWeek() == SATURDAY || first.getDayOfWeek() == SUNDAY) {
            first = first.with(TemporalAdjusters.next(MONDAY));
        }

        if (second.getDayOfWeek() == SATURDAY || second.getDayOfWeek() == SUNDAY) {
            second = second.with(TemporalAdjusters.previous(FRIDAY));
        }

        try {
            final long days = first.until(second, ChronoUnit.DAYS);
            final long weeks = first.with(TemporalAdjusters.previousOrSame(MONDAY)).until(second.with(TemporalAdjusters.nextOrSame(FRIDAY)), ChronoUnit.WEEKS);

            return days - weeks * 2 + 1;
        } catch (Exception e) {
            return -1;
        }
    }

    static int weekNumberOfLocalDate(LocalDate localDate) {
        return localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    static Pair<LocalDate, LocalDate> localDateOfYearWeekNumber(int weekNumber, int y) {
        final LocalDate start = Year.of(y).atDay(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
                .plusWeeks(weekNumber - 1);

        return new Pair<>(start, start.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
    }
}
