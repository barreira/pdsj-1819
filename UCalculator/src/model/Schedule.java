package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Schedule � a classe respons�vel por representar um hor�rio organizado por dias e
 * dividido em slots.
 */
class Schedule implements Serializable {

    private static final int DEFAULT_SLOT_SIZE = 60;
    private static final int MINUTES_OF_DAY = 1440;
    
    private final int slotSize;
    private int startSlotId;
    private int endSlotId;
    private final Map<LocalDate, List<Slot>> schedule;

    /**
     * Inicializa um Schedule.
     *
     * @param slotSize Tamanho do slot em minutos.
     */
    Schedule(final int slotSize) {
        this.slotSize = MINUTES_OF_DAY % slotSize == 0 ? slotSize : DEFAULT_SLOT_SIZE;
        this.startSlotId = 0;
        this.endSlotId = MINUTES_OF_DAY / this.slotSize - 1;
        this.schedule = new HashMap<>();
    }

    /**
     * Inicializa um Schedule restringindo os slots dispon�veis.
     *
     * @param slotSize  Tamanho do slot em minutos.
     * @param startTime A hora de in�cio do hor�rio.
     * @param endTime   A hora de fim do hor�rio.
     */
    Schedule(final int slotSize, final LocalTime startTime, final LocalTime endTime) {
        this(slotSize);
        this.setStartTime(startTime);
        this.setEndTime(endTime);
    }

    int getSlotSize() {
        return slotSize;
    }

    LocalTime getStartTime() {
        return slotTime(startSlotId);
    }

    LocalTime getEndTime() {
        return slotTime(endSlotId + 1);
    }

    /**
     * M�todo respons�vel por abrir slots previamente fechados.
     * Se encontrar algum slot que se est� no estado ocupado avan�a para o pr�ximo.
     *
     * @param date     Data a abrir os slots previamente fechados.
     * @param slotId   Id do slot inicial.
     * @param duration N�mero de slots a aplicar.
     * @return N�mero de slots abertos.
     */
    int openSlots(final LocalDate date, final int slotId, final int duration) {
        int slotsOpened = 0;

        if (startSlotId + slotId >= startSlotId && startSlotId + slotId <= endSlotId && duration >= 1) {
            int k = slotId + startSlotId;
            LocalDate next = date;
            List<Slot> slots;

            for (int i = 0; i < duration; i++) {
                if ((slots = schedule.get(next)) != null) {
                    for (int j = k; j <= endSlotId && i < duration; j++, i++) {
                       if (slots.get(j).getClass().equals(ClosedSlot.class)) {
                           slots.set(j, new OpenSlot(j, this.slotTime(j), this.slotTime(j + 1)));
                           slotsOpened++;
                       }
                    }

                    next = next.plusDays(1);
                    k = startSlotId;
                } else {
                    i += endSlotId - startSlotId;
                }
            }
        }

        return slotsOpened;
    }

    /**
     * M�todo respons�vel por fechar slots.
     * Apenas efetua a opera��o se n�o encontrar nenhum slot ocupado.
     *
     * @param date     Data a fechar os slots.
     * @param slotId   Id do slot inicial.
     * @param duration N�mero de slots a aplicar a opera��o.
     * @return Se foi poss�vel concluir a opera��o.
     */
    boolean closeSlots(final LocalDate date, final int slotId, final int duration) {
        boolean success;

        if (startSlotId + slotId < startSlotId || slotId + startSlotId > endSlotId || duration < 1) {
            success = false;
        } else {
            success = this.testSlots(date, slotId, duration);

            if (success) {
                int k = slotId + startSlotId;
                LocalDate next = date;
                List<Slot> slots;

                for (int i = 0; i < duration; i++) {
                    slots = this.addOpenSlots(next);

                    for (int j = k; j <= endSlotId && i < duration; j++, i++) {
                        slots.set(j, new ClosedSlot(j, this.slotTime(j), this.slotTime(j + 1)));
                    }

                    next = next.plusDays(1);
                    k = startSlotId;
                    i--;
                }
            }
        }

        return success;
    }

    /**
     * M�todo respons�vel por adicionar uma tarefa ao hor�rio.
     * A tarefa poder� ocupar v�rios slots e transportar-se para outros dias.
     *
     * @param date     Data de in�cio da tarefa.
     * @param slotId   Id do slot inicial.
     * @param duration Dura��o da tarefa representada atrav�s do n�mero de slots.
     * @param title    T�tulo descritivo da tarefa.
     * @param people   Lista de pessoas envolvidas na tarefa.
     * @return Se a opera��o foi efetuada com sucesso devolve true sen�o false.
     */
    boolean addTask(final LocalDate date, final int slotId, final int duration, 
                    final String title, final List<String> people) {
        boolean success;

        if (startSlotId + slotId < startSlotId || startSlotId + slotId > endSlotId || duration < 1) {
            success = false;
        } else {
            success = this.testSlots(date, slotId, duration);

            if (success) {
                final Task task = new Task(date, slotId, duration, title, people);
                int k = slotId + startSlotId;
                LocalDate next = date;
                List<Slot> slots;

                for (int i = 0; i < duration; i++) {
                    slots = this.addOpenSlots(next);

                    for (int j = k; j <= endSlotId && i < duration; j++, i++) {
                        slots.set(j, new BusySlot(j, this.slotTime(j),
                                this.slotTime(j + 1), task));
                    }

                    next = next.plusDays(1);
                    k = startSlotId;
                    i--;
                }
            }
        }

        return success;
    }

    /**
     * M�todo auxiliar para determinar se numa determinada dura��o todos os slots
     * est�o livres.
     *
     * @param date     Data do hor�rio.
     * @param slotId   Id do slot inicial.
     * @param duration N�mero de slots a aplicar a opera��o.
     * @return Se todos os slots estiverem livres devolve true sen�o devolve false.
     */
    private boolean testSlots(final LocalDate date, final int slotId, final int duration) {
        boolean success = true;
        LocalDate next = date;
        int k = slotId + startSlotId;
        List<Slot> slots;

        for (int i = 0; i < duration && success; i++) {
            if ((slots = schedule.get(next)) != null) {
                for (int j = k; j <= endSlotId && i < duration; j++, i++) {
                    if (!slots.get(j).getClass().equals(OpenSlot.class)) {
                        success = false;
                        break;
                    }
                }
            } else {
                i += endSlotId - startSlotId;
            }

            next = next.plusDays(1);
            k = startSlotId;
        }

        return success;
    }

    /**
     * M�todo auxiliar que inicializa os slots numa determinada data
     * ao hor�rio.
     *
     * @param date Data a inicializar os slots.
     * @return Devolve A lista de slots criados e adicionados � estrutura interna do hor�rio.
     */
    private List<Slot> addOpenSlots(LocalDate date) {
        final int totalSlots = MINUTES_OF_DAY / this.slotSize;
        List<Slot> slots = schedule.get(date);

        if (slots == null) {
            slots = new ArrayList<>();

            for (int j = 0; j < totalSlots; j++) {
                slots.add(new OpenSlot(j, this.slotTime(j), this.slotTime(j + 1)));
            }

            this.schedule.put(date, slots);
        }

        return slots;
    }

    /**
     * M�todo respons�vel por remover uma tarefa.
     * Para tal basta indicar um dia e slot onde essa tarefa esteja contida.
     * Todos os restantes slots tamb�m sofrer�o altera��es.
     *
     * @param date   Uma data onde se encontre parte da tarefa.
     * @param slotId Id do slot onde a tarefa esteja contida.
     * @return A tarefa removida.
     */
    Task removeTask(final LocalDate date, final int slotId) {
        Task task = null;

        if (slotId + startSlotId >= startSlotId && startSlotId + slotId <= endSlotId) {
            List<Slot> slots = schedule.get(date);
            Slot slot;

            if (slots != null && (slot = slots.get(slotId)).getClass().equals(BusySlot.class)) {
                task = ((BusySlot) slot).getTask();
                int k = task.getSlotId();
                LocalDate next = task.getDate();

                for (int i = 0; i < task.getDuration(); i++) {
                    slots = schedule.get(next);

                    for (int j = k; j <= endSlotId && i < task.getDuration(); j++, i++) {
                        slots.set(j, new OpenSlot(j, this.slotTime(j), this.slotTime(j + 1)));
                    }

                    i--;
                    k = startSlotId;
                    next = next.plusDays(1);
                }
            }
        }

        return task;
    }

    /**
     * M�todo respons�vel por editar a data e slot de in�cio de uma tarefa.
     *
     * @param date        Data onde se encontre a tarefa.
     * @param slotId      Id do slot onde se encontre a tarefa.
     * @param newDate     Nova data de in�cio da tarefa.
     * @param newSlotId   Novo id do slot de in�cio da tarefa.
     * @param newDuration Nova dura��o da tarefa traduzida em n�mero de slots.
     * @return Se foi poss�vel editar a tarefa devolve true sen�o devolve false.
     */
    boolean editTask(final LocalDate date, final int slotId, final LocalDate newDate,
                            final int newSlotId, final int newDuration) {
        boolean success = true;

        if (newDuration >= 1 && newSlotId + startSlotId >= startSlotId && newSlotId + startSlotId <= endSlotId) {
            final Task task = this.removeTask(date, slotId);

            if (task == null) {
                success = false;
            } else if (!this.addTask(newDate, newSlotId, newDuration, task.getTitle(), task.getPeople())) {
                success = !this.addTask(task);
            }
        } else {
            success = false;
        }

        return success;
    }

    /**
     * M�todo respons�vel por editar o t�tulo e lista de pessoas envolvidas na tarefa.
     *
     * @param date   Data em que se encontra a tarefa.
     * @param slotId Id externo do slot em que se encontre a tarefa.
     * @param title  Novo t�tulo da tarefa.
     * @param people Nova lista de indiv�duos envolvidos na tarefa.
     * @return Se foi poss�vel editar a tarefa devolve true sen�o devolve false.
     */
    boolean editTask(final LocalDate date, final int slotId, final String title, final List<String> people) {
        boolean success = false;
        List<Slot> slots;

        if (startSlotId + slotId >= startSlotId && slotId + startSlotId <= endSlotId &&
                (slots = this.schedule.get(date)) != null) {
            if (slots.get(slotId).getClass().equals(BusySlot.class)) {
                final Task task = ((BusySlot) slots.get(slotId)).getTask();

                task.setTitle(title);
                task.setPeople(people);
                success = true;
            }
        }

        return success;
    }

    /**
     * M�todo respons�vel por consultar uma determinada data no hor�rio.
     *
     * @param date Data a consultar.
     * @return Devolve uma c�pia de todos os slots.
     */
    List<Slot> consult(final LocalDate date) {
        final List<Slot> result = new ArrayList<>();
        final List<Slot> slots = schedule.get(date);

        if (slots == null) {
            for (int i = startSlotId; i <= endSlotId; i++) {
                result.add(new OpenSlot(i, this.slotTime(i), this.slotTime(i + 1)));
            }
        } else {
            for (int i = startSlotId; i <= endSlotId; i++) {
                result.add(slots.get(i).clone());
            }
        }

        return result;
    }

    /**
     * M�todo respons�vel por definir o slot de in�cio do hor�rio.
     * Se n�o for poss�vel definir na hora espec�ficada, o slot imediatamente anterior
     * ser� o espec�ficado para a nova hora de in�cio.
     *
     * @param time Nova hora de in�cio.
     */
    void setStartTime(LocalTime time) {
        List<LocalTime> startSlots =
                IntStream.range(0, 1440 / slotSize)
                .mapToObj(i -> LocalTime.of(0, 0).plusMinutes(i * slotSize))
                .collect(Collectors.toList());

        this.startSlotId = IntStream
                .range(1, startSlots.size())
                .filter(i -> time.isBefore(startSlots.get(i)))
                .map(i -> i - 1)
                .findFirst()
                .orElse(this.startSlotId);
    }

    /**
     * M�todo respons�vel por definir o slot de fim do hor�rio.
     * Se n�o for poss�vel definir na hora espec�ficada, o slot imediatamente posterior
     * ser� o espec�ficado para a nova hora de fim.
     *
     * @param time Nova hora de fim.
     * @return Devolve o id interno do slot de fim do hor�rio.
     */
    void setEndTime(LocalTime time) {
        List<LocalTime> endSlots =
                IntStream.range(0, 1440 / slotSize)
                        .mapToObj(i -> LocalTime.of(0, 0).plusMinutes((i + 1) * slotSize))
                        .collect(Collectors.toList());

        this.endSlotId = IntStream
                .range(1, endSlots.size())
                .filter(i -> time.isBefore(endSlots.get(i)) || time.equals(endSlots.get(i)))
                .findFirst()
                .orElse(this.endSlotId);
    }

    /**
     * M�todo privado e auxiliar respons�vel por adicionar novamente uma tarefa ao hor�rio.
     *
     * @param task Tarefa a adicionar.
     * @return Se foi poss�vel concluir a opera��o devolve true sen�o devolve false.
     */
    private boolean addTask(Task task) {
        return this.addTask(task.getDate(), task.getSlotId(), task.getDuration(), task.getTitle(), task.getPeople());
    }

    /**
     * M�todo privado respons�vel por traduzir o id interno de um slot para a sua hora de in�cio.
     *
     * @param slotId Id interno do slot.
     * @return Hora em que o slot se in�cia.
     */
    private LocalTime slotTime(final int slotId) {
        return LocalTime.of(0, 0).plusMinutes(slotId * this.slotSize);
    }
}





