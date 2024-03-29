package model;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

/**
 * Classe que contém todos os métodos de acesso às funcionalidades de uma calculadora de datas, uma conversora
 * de fusos horários e uma agenda.
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
public final class UCalculatorModel {
    private Config config;
    private LocalDateCalculator localDateCalculator;
    private TimeZoneCalculator timeZoneCalculator;
    private Schedule schedule;

    /**
     * Inicializa todas as funcionalidades:
     *  - Calculadora de datas.
     *  - Conversora de fusos horários.
     *  - Agenda.
     */
    public UCalculatorModel() {
        config = Config.getInstance();
        localDateCalculator = new LocalDateCalculator();
        timeZoneCalculator = new TimeZoneCalculator();
        schedule = config.readSchedule();
    }

    /**
     * Inicializa a calculadora de datas com uma data inicial. A partir desta data inicial podem-se efetuar
     * operações de soma ou subtração de unidades de tempo (dias, semanas ou meses por exemplo).
     *
     * @param localDate Data inicial para se efetuar uma ou mais operações.
     */
    public void initLocalDateCalculator(LocalDate localDate) {
        localDateCalculator.push(localDate);
    }

    /**
     * Soma um determinado valor de unidades de tempo a uma data.
     *
     * @param duration   Duração a somar a uma data.
     * @param chronoUnit Unidade de tempo da duração a somar.
     */
    public void addDuration(final int duration, final ChronoUnit chronoUnit) {
        localDateCalculator.push(DateUtils.datePlusDuration, duration, chronoUnit);
    }

    /**
     * Subtrai um determinado valor de unidades de tempo a uma data.
     *
     * @param duration   Duração a subtrair a uma data.
     * @param chronoUnit Unidade de tempo da duração a somar.
     */
    public void subtractDuration(final int duration, final ChronoUnit chronoUnit) {
        localDateCalculator.push(DateUtils.dateMinusDuration, duration, chronoUnit);
    }

    /**
     * Soma uma quantidade de dias úteis à data fornecida à calculadora de datas.
     *
     * @param workingDays Número de dias úteis a somar a uma determinada data.
     */
    public void addWorkingDays(final int workingDays) {
        localDateCalculator.push(DateUtils.datePlusWorkingDays, workingDays);
    }

    /**
     * Subtrai uma quantidade de dias úteis à data fornecida à calculadora de datas.
     *
     * @param workingDays Número de dias úteis a subtrair a uma determinada data.
     */
    public void subtractWorkingDays(final int workingDays) {
        localDateCalculator.push(DateUtils.dateMinusWorkingDays, workingDays);
    }

    /**
     * Soma uma quantidade de quinzenas à data fornecida à calculadora de datas.
     *
     * @param fortnights Número de quinzenas a somar a uma determinada data.
     */
    public void addFortnights(final int fortnights) {
        localDateCalculator.push(DateUtils.datePlusFortnights, fortnights);
    }

    /**
     * Subtrai uma quantidade de quinzenas à data fornecida à calculadora de datas.
     *
     * @param fortnights Número de quinzenas a subtrair a uma determinada data.
     */
    public void subtractFortnights(final int fortnights) {
        localDateCalculator.push(DateUtils.dateMinusFortnights, fortnights);
    }

    /**
     * Anula a última operação efetuada na calculadora de datas.
     */
    public void previous() {
        localDateCalculator.pop();
    }

    /**
     * Efetua todas as operações fornecidas à calculadora de datas. A calculadora não é limpa com esta operação.
     *
     * @return Data calculada através da calculadora de datas. Devolve null caso não haja nada para calcular.
     */
    public LocalDate solve() {
        final LocalDate localDate = localDateCalculator.peek();

        localDateCalculator.clear();

        return localDate;
    }

    /**
     * Limpa todas as operações efetuadas na calculadora de datas.
     */
    public void clear() {
        localDateCalculator.clear();
    }

    /**
     * Dadas duas datas, calcula o período de tempo entre essas mesmas datas.
     *
     * @param first  Primeira data.
     * @param second Segunda data.
     * @return Período de tempo decorrido entre a primeira e a segunda data fornecidas.
     */
    public Period intervalPeriod(final LocalDate first, final LocalDate second) {
        return DateUtils.intervalBetweenDates.apply(first, second);
    }


    /**
     * Dadas duas datas, calcula o período de tempo entre essas mesmas datas numa determinada unidade de tempo.
     *
     * @param first      Primeira data.
     * @param second     Segunda data.
     * @param chronoUnit Unidade de tempo a devolver.
     * @return Período de tempo numa determinada unidade de tempo decorrido
     *         entre a primeira e a segunda data fornecidas.
     */
    public long intervalInUnit(final LocalDate first, final LocalDate second, final ChronoUnit chronoUnit) {
        return DateUtils.intervalInUnit(first, second, chronoUnit);
    }

    /**
     * Dadas duas datas, calcula o número de dias úteis entre essas datas.
     *
     * @param first  Primeira data.
     * @param second Segunda data.
     * @return Número de dias úteis que decorrem entre a primeira e segunda datas fornecidas.
     */
    public long intervalInWorkingDays(final LocalDate first, final LocalDate second) {
        return DateUtils.intervalInWorkingDays(first, second);
    }

    /**
     * Dadas duas datas, calcula o número de quinzenas entre essas datas.
     *
     * @param first  Primeira data.
     * @param second Segunda data.
     * @return Número de quinzenas que decorrem entre a primeira e segunda datas fornecidas.
     */
    public long intervalInFortnights(final LocalDate first, final LocalDate second) {
        return DateUtils.intervalInFortnights(first, second);
    }

    /**
     * Calcula o número da semana do ano a que uma determinada data pertence.
     *
     * @param localDate Data que se pretende saber o número da semana do ano.
     * @return Número da semana de uma determinada data de um determinado ano.
     */
    public int getWeekNumber(final LocalDate localDate) {
        return DateUtils.weekNumberOfLocalDate(localDate);
    }

    /**
     * Dado um número de uma semana e um ano, calcula a data do início dessa semana e data do final dessa semana.
     *
     * @param weekNumber Número da semana do ano.
     * @param year       Ano a que pertence uma determinada semana.
     * @return Pair com a data no início da semana como chave e data do final da semana como valor.
     */
    public SimpleEntry<LocalDate, LocalDate> dateOfWeekNumber(final int weekNumber, final int year) {
        return DateUtils.dateOfWeekNumber(weekNumber, year);
    }

    /**
     * Para um determinado ano, determinado mês e um determinado dia da semana, devolve a sua data de acordo com a sua
     * posição nesse mês.
     *
     * Exemplo: Calcular a data da 2ª Terça-feira do mês de janeiro do ano de 2017.
     *
     * @param year      Ano pretendido.
     * @param month     Mês pretendido.
     * @param dayOfWeek Dia da semana pretendido.
     * @param place     Número da semana do mês pretendido.
     * @return Data do dia da semana ou null caso os parâmetros sejam inválidos.
     */
    public LocalDate dayOfWeekInMonth(final int year, final int month, final DayOfWeek dayOfWeek, final int place) {
        return DateUtils.dateOfDayOfWeekInMonth(year, month, dayOfWeek, place);
    }

    /**
     * Para um determinado ano, determinado mês e um determinado dia da semana, devolve uma lista com todas as datas
     * desses dias da semana.
     *
     * Exemplo: Calcular todas as datas de todas as Segundas-feira do mês de Dezembro do ano de 2016.
     *
     * @param year      Ano pretendido.
     * @param month     Mês pretendido.
     * @param dayOfWeek Dia da semana pretendido.
     * @return Lista com todos as datas ou uma lista vazia caso não seja possível calcular tais datas.
     */
    public List<LocalDate> getAllDaysOfWeekInMonth(final int year, final int month, final DayOfWeek dayOfWeek) {
        return DateUtils.getAllDaysOfWeekInMonth(year, month, dayOfWeek);
    }

    /* ********************************************* TimeZone Calculator *********************************************/

    /**
     * Inicializa a conversora de fusos horários com todos os fusos horários disponíveis.
     */
    public void initTimeZoneIDs() {
        timeZoneCalculator.initTimeZoneIDs();
    }

    /**
     * Dada uma string devolve todos os ids de todos os fusos horários que façam match com essa string ou que contenham
     * essa string. A verificação não é case sensitive.
     *
     * @param id Nome de um fuso horário ou parte do nome de um conjunto de fusos horários.
     * @return Lista com todos os fusos horários que façam match com o valor recebido.
     */
    public List<String> getMatchedTimezoneIDs(final String id) {
        return timeZoneCalculator.getMatchedZoneIds(id);
    }

    /**
     * Dada uma determinada data e um determino id de um fuso horário, converte essa mesma data para esse mesmo fuso.
     *
     * @param id            Fuso horário para converter.
     * @param localDateTime Data a converter.
     * @return Data convertida para o fuso horário ou a própria data caso o fuso horário seja inválido.
     */
    public LocalDateTime getTimeZone(final String id, LocalDateTime localDateTime) {
        return DateUtils.convertToTimezone(id, localDateTime);
    }

    /**
     * Através de uma data de partida de uma determinada viagem, do tempo de viagem e do fuso horário de destino,
     * calcula a hora de chegada ao destino (no fuso horário de destino).
     *
     * @param timezoneId    Fuso horário do destino.
     * @param departureTime Hora de partida.
     * @param travelTime    Tempo de viagem.
     * @return Data e hora de chegada no fuso horário de destino. Caso os valores recebidos sejam inválidos, devolve
     *         a data e hora fornecida.
     */
    public LocalDateTime getArrivalTime(String timezoneId, LocalDateTime departureTime, LocalTime travelTime) {
        return DateUtils.getArrivalTime(timezoneId, departureTime, travelTime);
    }


    /**
     * Calcula os tempos (LocalDateTime) de início e fim de um voo.
     *
     * @param beginTime   Tempo de fim do voo anterior
     * @param timeBetween Tempo entre o fim do voo anterior e o início do voo atual
     * @param duration    Duração do voo
     * @return SimpleEntry contendo LocalDateTime de início e de fim do voo
     */
    public SimpleEntry<LocalDateTime, LocalDateTime> getTravelDateTimes(LocalDateTime beginTime, LocalTime timeBetween, LocalTime duration) {
        return DateUtils.getTravelDateTimes(beginTime, timeBetween, duration);
    }

    /* ********************************************* Schedule ********************************************************/

    /**
     * Torna um conjunto de slots disponíveis para se poderem alocar.
     *
     * @param date     Data pretendida.
     * @param slotId   Id do slot inicial que pretende disponibilizar.
     * @param duration Quantidade de slots a partir do slot inicial que pretende disponibilizar.
     * @return Devolve o conjunto de slots tornados disponíveis.
     */
    public int openSlots(final LocalDate date, final int slotId, final int duration) {
        int openedSlots = schedule.openSlots(date, slotId, duration);
        if (openedSlots > 0) {
            config.storeSchedule(schedule);
        }
        return openedSlots;
    }

    /**
     * Torna um conjunto de slots indisponíveis.
     *
     * @param date     Data pretendida.
     * @param slotId   Id do slot inicial que pretende preencher.
     * @param duration Quantidade de slots a partir do slot inicial que pretende preencher de modo a não se permitir
     *                 a sua alocação.
     * @return True caso a operação seja efetuada com sucesso e false caso contrário.
     */
    public boolean closeSlots(final LocalDate date, final int slotId, final int duration) {
        boolean slotsClosed = false;
        if (schedule.closeSlots(date, slotId, duration)) {
            slotsClosed = true;
            config.storeSchedule(schedule);
        }
        return slotsClosed;
    }

    /**
     * Adicionada uma determinada tarefa à agenda. Uma tarefa pode transitar para um dia seguinte caso seja necessário.
     * Uma tarefa não pode ser adicionada caso esta se sobreponha a outras possíveis tarefas já adicionadas ou caso
     * se sobreponha a um conjunto de slots descartados pela funcão "closeSlots".
     *
     * @param date     Data pretendida.
     * @param slotId   Id do slot inicial que pretende adicionar uma tarefa.
     * @param duration Quantidade de slots a partir do slot inicial que a tarefa ocupa.
     * @param title    Título da tarefa.
     * @param people   Lista com os nomes das pessoas envolvidas na tarefa a adicionar.
     * @return True caso a operação seja efetuada com sucesso e false caso contrário.
     */
    public boolean addTask(final LocalDate date, final int slotId, final int duration,
                           final String title, final List<String> people) {
        boolean taskAdded = true;
        if (schedule.addTask(date, slotId, duration, title, people)) {
            config.storeSchedule(schedule);
        } else {
            taskAdded = false;
        }
        return taskAdded;
    }

    /**
     * Remove uma determinada tarefa se esta existir.
     *
     * @param date   Data pretendida.
     * @param slotId Slot selecionado.
     * @return True caso a tarefa seja removida com sucesso ou false caso esta não exista.
     */
    public boolean removeTask(final LocalDate date, final int slotId) {
        boolean taskRemoved = true;
        if (schedule.removeTask(date, slotId) != null) {
            config.storeSchedule(schedule);
        } else {
            taskRemoved = false;
        }
        return taskRemoved;
    }

    /**
     * Devolve uma lista com as informações dos slots de uma determinada data.
     *
     * @param date Data pretendida.
     * @return Lista com os slots desse dia.
     */
    public List<Slot> consult(final LocalDate date) {
        return schedule.consult(date);
    }

    /**
     * Permite alterar uma data de uma tarefa assim como o seu slot e a sua duração. Isto só acontece caso estas novas
     * configurações não entrem em conflito com outras entradas já pré-existentes na agenda.
     *
     * @param date        Data pretendida.
     * @param slotId      Slot a editar
     * @param newDate     Nova data da tarefa.
     * @param newSlotId   Novo slot da tarefa
     * @param newDuration Nova duração da tarefa a editar.
     * @return True caso seja possível editar essa tarefa ou false caso contrário.
     */
    public boolean editTask(final LocalDate date, final int slotId, final LocalDate newDate,
                        final int newSlotId, final int newDuration) {
        boolean taskEdited = true;
        if (schedule.editTask(date, slotId, newDate, newSlotId, newDuration)) {
            config.storeSchedule(schedule);
        } else {
            taskEdited = false;
        }
        return taskEdited;
    }

    /**
     * Permite modificar o título e a lista de pessoas envolvidas numa tarefa.
     *
     * @param date   Data pretendida.
     * @param slotId Slot a editar.
     * @param title  Novo título.
     * @param people Nova lista de pessoas.
     * @return True caso seja possível editar essa tarefa ou false caso contrário.
     */
    public boolean editTask(final LocalDate date, final int slotId, final String title, final List<String> people) {
        boolean taskEdited = true;
        if(schedule.editTask(date, slotId, title, people)) {
            config.storeSchedule(schedule);
        } else {
            taskEdited = false;
        }
        return taskEdited;
    }

    public String getDateTimePattern() {
        return config.getDateTimePattern();
    }

    public String getDatePattern() {
        return config.getDatePattern();
    }

    public String getTimePattern() {
        return config.getTimePattern();
    }

    public boolean setDateTimePattern(String pattern) {
        return config.setDateTimePattern(pattern);
    }

    public boolean setDatePattern(String pattern) {
        return config.setDatePattern(pattern);
    }

    public boolean setTimePattern(String pattern) {
        return config.setTimePattern(pattern);
    }

    public boolean setSlotSize(int slotSize) {
        boolean success = false;
        if(config.setSlotSize(slotSize)) {
            success = true;
            schedule = config.readSchedule();
            config.storeSchedule(schedule);
        }
        return success;
    }

    public boolean setStartTime(LocalTime time) {
        // O resultado de schedule.setStartTime nunca vai ser falso caso
        // o resultado de config.setStartTime seja verdadeiro.
        return config.setStartTime(time) && schedule.setStartTime(time);
    }

    public boolean setEndTime(LocalTime time) {
        return config.setEndTime(time) && schedule.setEndTime(time);
    }

    public int getSlotSize() {
        return schedule.getSlotSize();
    }

    public LocalTime getStartTime() {
        return schedule.getStartTime();
    }

    public LocalTime getEndTime() {
        return schedule.getEndTime();
    }

    public void setDefault() {
        config.setDefault();
    }
}
