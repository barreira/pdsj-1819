package model;

import java.time.*;
import java.time.temporal.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.AbstractMap.SimpleEntry;

import static java.time.DayOfWeek.*;

/**
 * Classe que possui funções utilitárias responsáveis por operações usando datas
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
final class DateUtils {

    /**
     * BiFunction que calcula o resultado (LocalDate) da adição de um dado número da unidade temporal fornecida a uma
     * LocalDate recebida.
     */
    static final BiFunction<LocalDate, SimpleEntry<Integer, TemporalUnit>, LocalDate> datePlusDuration =
            (x, y) -> x.isSupported(y.getValue()) ? x.plus(y.getKey(), y.getValue()) : x;

    /**
     * BiFunction que calcula o resultado (LocalDate) da subtração de um dado número da unidade temporal fornecida a uma
     * LocalDate recebida.
     */
    static final BiFunction<LocalDate, SimpleEntry<Integer, TemporalUnit>, LocalDate> dateMinusDuration =
            (x, y) -> x.isSupported(y.getValue()) ? x.minus(y.getKey(), y.getValue()) : x;

    /**
     * BiFunction que calcula o resultado (LocalDate) da adição de um dado número de dias úteis a uma LocalDate
     * recebida.
     */
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

    /**
     * BiFunction que calcula o resultado (LocalDate) da subtração de um dado número de dias úteis a uma LocalDate
     * recebida.
     */
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

    /**
     * BiFunction que calcula o resultado (LocalDate) da adição de um número de fortnights (períodos de duas semanas)
     * a uma LocalDate recebida.
     */
    static final BiFunction<LocalDate, Integer, LocalDate> datePlusFortnights =
            (x, y) -> x.plusDays(y * 14);

    /**
     * BiFunction que calcula o resultado (LocalDate) da subtração de um número de fortnights (períodos de duas semanas)
     * a uma LocalDate recebida.
     */
    static final BiFunction<LocalDate, Integer, LocalDate> dateMinusFortnights =
            (x, y) -> x.minusDays(y * 14);

    /**
     * BiFunction que calcula o período (Period) entre duas LocalDates recebidas.
     */
    static final BiFunction<LocalDate, LocalDate, Period> intervalBetweenDates = Period::between;

    /**
     * Calcula o intervalo temporal entre duas LocalDates recebidas em ChronoUnit específica.
     *
     * @param first      Primeira data.
     * @param second     Segunda data.
     * @param chronoUnit Unidade do resultado a devolver.
     *
     * @return long Invervalo temporal entre as duas datas, na ChronoUnit recebida.
     *         -1   Caso ocorra uma exceção.
     */
    static long intervalInUnit(LocalDate first, LocalDate second, ChronoUnit chronoUnit) {
        try {
            return first.until(second, chronoUnit);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Calcula o número de fortnights (períodos de duas semanas) entre duas LocalDates recebidas.
     *
     * @param first  Primeira data.
     * @param second Segunda data.
     *
     * @return long Número de fortnights (períodos de duas semanas) entre as duas datas recebidas.
     *         -1   Caso ocorra uma exceção.
     */
    static long intervalInFortnights(LocalDate first, LocalDate second) {
        try {
            return first.until(second, ChronoUnit.WEEKS) / 2;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Calcula o número de dias úteis (working days) entre duas LocalDates recebidas.
     *
     * @param first  Primeira data.
     * @param second Segunda data.
     *
     * @return long Número de dias úteis entre as duas datas recebidas.
     *         -1   Caso ocorra uma exceção.
     */
    static long intervalInWorkingDays(LocalDate first, LocalDate second) {
        if (first.getDayOfWeek() == SATURDAY || first.getDayOfWeek() == SUNDAY) {
            first = first.with(TemporalAdjusters.next(MONDAY));
        }

        if (second.getDayOfWeek() == SATURDAY || second.getDayOfWeek() == SUNDAY) {
            second = second.with(TemporalAdjusters.previous(FRIDAY));
        }

        try {
            final long days = first.until(second, ChronoUnit.DAYS);
            final long weeks = first.with(TemporalAdjusters.previousOrSame(MONDAY))
                                    .until(second.with(TemporalAdjusters.nextOrSame(FRIDAY)), ChronoUnit.WEEKS);

            return days - weeks * 2 + 1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Dada uma LocalDate, calcula o número da semana no ano correspondente.
     *
     * @param localDate LocalDate.
     *
     * @return int Número da semana do ano correspondente à data recebida.
     */
    static int weekNumberOfLocalDate(LocalDate localDate) {
        return localDate != null ? localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) : -1;
    }

    /**
     * Dado o número da semana no ano (ambos recebidos como parâmetro), devolve uma SimpleEntry com duas LocalDates
     * correspondentes à data de início e data de fim dessa semana.
     *
     * @param weekNumber Número da semana.
     * @param year       Ano.
     *
     * @return SimpleEntry<LocalDate, LocalDate> Data de início e data de fim da semana no ano.
     */
    static SimpleEntry<LocalDate, LocalDate> dateOfWeekNumber(int weekNumber, int year) {
        final LocalDate start = Year.of(year).atDay(1).with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
                                                             .plusWeeks(weekNumber - 1);

        return new SimpleEntry<>(start, start.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
    }

    /**
     * Dado um ano, um mês e um dia da semana, devolve a LocalDate referente à data em que aquele dia da semana ocorre
     * pela primeira, segunda, terceira, quarta ou quinta vez (recebido como parâmetro) no mês.
     *
     * @param year      Ano.
     * @param month     Código do mês (1 a 12).
     * @param dayOfWeek Dia da semana.
     * @param place     Número da ocorrência do dia da semana do mês a obter (primeira, segunda, terceira, quarta ou
     *                  quinta).
     * @return LocalDate LocalDate correspondente à ocorrência do dia da semana no mês.
     *         null      Caso o dia da semana não ocorra na posição recebida no mês.
     */
    static LocalDate dateOfDayOfWeekInMonth(final int year, final int month, final DayOfWeek dayOfWeek, int place) {
        try {
            LocalDate localDate = YearMonth.of(year, month).atDay(1)
                    .with(TemporalAdjusters.nextOrSame(dayOfWeek));

            for (int i = 0; i < place - 1; i++) {
                localDate = localDate.with(TemporalAdjusters.next(dayOfWeek));
            }

            return isDateInMonth(localDate, month) ? localDate : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Dado um ano, um mês e um dia da semana, devolve uma lista com todas as datas referente à data em que aquele
     * dia da semana ocorre no mês.
     *
     * @param year      Ano.
     * @param month     Código do mês (1 a 12).
     * @param dayOfWeek Dia da semana.
     * @return Lista com todas as datas ou lista vazia caso os parâmetros recebidos sejam inválidos.
     */
    static List<LocalDate> getAllDaysOfWeekInMonth(final int year, final int month, final DayOfWeek dayOfWeek) {
        final List<LocalDate> list = new ArrayList<>();

        try {
            LocalDate localDate =
                    YearMonth.of(year, month).atDay(1).with(TemporalAdjusters.nextOrSame(dayOfWeek));

            list.add(localDate);

            while (true) {
                localDate = localDate.with(TemporalAdjusters.next(dayOfWeek));

                if (!DateUtils.isDateInMonth(localDate, month)) {
                    break;
                }

                list.add(localDate);
            }

            return list;
        } catch (Exception e) {
            return list;
        }
    }


    /**
     * Dada uma LocalDate e um mês, indica se a data pertence a esse mês.
     *
     * @param localDate LocalDate
     * @param month     Código (1 a 12) do mês
     *
     * @return true  caso a data pertença ao mês
     *         false caso contrário
     */
    private static Boolean isDateInMonth(LocalDate localDate, int month) {
        return (localDate.getMonth().getValue() == month);
    }


    /**
     * Converte uma LocalDateTime para uma timezone recebida.
     *
     * @param timezoneID    Código da timezone
     * @param localDateTime LocalDateTime a converter
     *
     * @return LocalDateTime LocalDateTime convertida
     */
    static LocalDateTime convertToTimezone(final String timezoneID, final LocalDateTime localDateTime) {
        try {
            final ZoneId zoneId = ZoneId.of(timezoneID);
            final ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
            return localDateTime.plusSeconds(zonedDateTime.getOffset().getTotalSeconds());
        } catch (NullPointerException | DateTimeException e) {
            return localDateTime;
        }
    }

    /**
     * Calcula a LocalDateTime de chegada a um destino dado o código da sua timezone, a LocalDateTime de partida e
     * o LocalTime de viagem (tempo de voo).
     *
     * @param timezoneId    Código da timezone do destino
     * @param departureTime LocalDateTime de partida (origem)
     * @param travelTime    Tempo de viagem
     *
     * @return LocalDateTime LocalDateTime de chegada ao destino
     */
    static LocalDateTime getArrivalTime(String timezoneId, LocalDateTime departureTime, LocalTime travelTime) {
        return DateUtils.convertToTimezone(timezoneId, departureTime).plusHours(travelTime.getHour())
                                                                     .plusMinutes(travelTime.getMinute());
    }
}
