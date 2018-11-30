package model;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;

public final class UCalculatorModel {

    private LocalDateCalculator localDateCalculator;
    private TimeZoneCalculator timeZoneCalculator;
    private Schedule schedule;

    public UCalculatorModel() {
        localDateCalculator = new LocalDateCalculator();
        timeZoneCalculator = new TimeZoneCalculator();
        schedule = new Schedule();
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
        final LocalDate localDate = localDateCalculator.peek();

        localDateCalculator.clear();

        return localDate;
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

    public Pair<LocalDate, LocalDate> dateOfWeekNumber(final int weekNumber, final int year) {
        return DateUtils.dateOfWeekNumber(weekNumber, year);
    }

    public List<LocalDate> daysOfWeekInMonth(final int year, final int month,
                                             final DayOfWeek dayOfWeek, final int place) {
        if (place != 6) { // se não existir, retorna lista = {null}
            LocalDate ld = DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, place);

            return (ld == null) ? null
                                : singletonList(DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, place));
        }
        else { // se não existir 5.ª ocorrência, retorna lista = {1.ª, 2.ª, 3.ª, 4.ª, null}
            return Arrays.asList(DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, 1),
                                 DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, 2),
                                 DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, 3),
                                 DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, 4),
                                 DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, 5));
        }
    }

    /* ********************************************* TimeZone Calculator *********************************************/

    public void initTimeZoneIDs() {
        timeZoneCalculator.initTimeZoneIDs();
    }

    public List<String> getMatchedTimezoneIDs(final String id) {
        return timeZoneCalculator.getMatchedZoneIds(id);
    }

    public LocalDateTime getTimeZone(final String id, LocalDateTime localDateTime) {
        return DateUtils.convertToTimezone(id, localDateTime);
    }

    public LocalDateTime getArrivalTime(String timezoneId, LocalDateTime departureTime, LocalTime travelTime) {
        return DateUtils.getArrivalTime(timezoneId, departureTime, travelTime);
    }

    /* ********************************************* Schedule ********************************************************/

    public boolean openSlots(final LocalDate date, final int slotId, final int duration) {
        return schedule.openSlots(date, slotId, duration);
    }

    public boolean closeSlots(final LocalDate date, final int slotId, final int duration) {
        return schedule.closeSlots(date, slotId, duration);
    }

    public boolean addTask(final LocalDate date, final int slotId, final int duration,
                           final String title, final List<String> people) {
        return schedule.addTask(date, slotId, duration, title, people);
    }

    public boolean removeTask(final LocalDate date, final int slotId) {
        return schedule.removeTask(date, slotId) != null;
    }

    public List<Slot> consult(final LocalDate date) {
        return schedule.consult(date);
    }

    public boolean edit(final LocalDate date, final int slotId, final LocalDate newDate,
                        final int newSlotId, final int newDuration) {
        return schedule.editTask(date, slotId, newDate, newSlotId, newDuration);
    }

    public boolean edit(final LocalDate date, final int slotId, final String title, final List<String> people) {
        return schedule.editTask(date, slotId, title, people);
    }
}
