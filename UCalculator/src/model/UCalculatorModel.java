package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public final class UCalculatorModel {

    private LocalDateCalculator localDateCalculator;

    public UCalculatorModel() {
        localDateCalculator = new LocalDateCalculator();
    }

    public void initLocalDateCalculator(LocalDate LocalDate) {
        localDateCalculator.push(LocalDate);
    }

    public void addDuration(final int duration, final ChronoUnit chronoUnit) {
        localDateCalculator.push(DateUtils.datePlusDuration, duration, chronoUnit);
    }

    public void subtractDuration(final int duration, final ChronoUnit chronoUnit) {
        localDateCalculator.push(DateUtils.dateMinusDuration, duration, chronoUnit);
    }

    public void addWorkingDays(final int workingDays) {
        localDateCalculator.push(DateUtils.datePlusWorkingDays, workingDays);
    }

    public void subtractWorkingDays(final int workingDays) {
        localDateCalculator.push(DateUtils.dateMinusWorkingDays, workingDays);
    }

    public void addFortnights(final int fortnights) {
        localDateCalculator.push(DateUtils.datePlusFortnights, fortnights);
    }

    public void subtractFortnights(final int fortnights) {
        localDateCalculator.push(DateUtils.dateMinusFortnights, fortnights);
    }

    public void previous() {
        localDateCalculator.pop();
    }

    public LocalDate solve() {
        return localDateCalculator.peek();
    }

    public void clear() {
        localDateCalculator.clear();
    }

    public Period intervalPeriod(final LocalDate first, final LocalDate second) {
        return DateUtils.intervalBetweenDates.apply(first, second);
    }

    public long intervalInUnit(final LocalDate first, final LocalDate second, final ChronoUnit chronoUnit) {
        return DateUtils.intervalInUnit(first, second, chronoUnit);
    }

    public long intervalInWorkingDays(final LocalDate first, final LocalDate second) {
        return DateUtils.intervalInWorkingDays(first, second);
    }

    public long intervalInFortnights(final LocalDate first, final LocalDate second) {
        return DateUtils.intervalInFortnights(first, second);
    }

    public int getWeekNumber(final LocalDate localDate) {
        return DateUtils.weekNumberOfLocalDate(localDate);
    }

    public Pair<LocalDate, LocalDate> localDateOfWeekNumber(final int weekNumber, final int year) {
        return DateUtils.localDateOfYearWeekNumber(weekNumber, year);
    }
}
