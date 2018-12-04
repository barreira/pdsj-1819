package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Classe que representa um slot ocupado com uma determinada tarefa.
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
public class BusySlot extends Slot implements Serializable {

    private final Task task;

    /**
     * Inicializa um slot ocupado com uma tarefa.
     *
     * @param id        Id do slot.
     * @param startTime Hora de início do slot.
     * @param endTime   Hora de fim do slot.
     * @param task      Tarefa a ser adicionada.
     */
    BusySlot(final int id, final LocalTime startTime, final LocalTime endTime, final Task task) {
        super(id, startTime, endTime);
        this.task = task;
    }

    /**
     * Construtor de cópia.
     *
     * @param busySlot Objeto a copiar.
     */
    BusySlot(BusySlot busySlot) {
        super(busySlot);
        this.task = busySlot.getTask().clone();
    }

    /**
     * @return Tarefa presente no slot.
     */
    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "BusySlot{" + "task=" + task + "} " + super.toString();
    }

    @Override
    public BusySlot clone() {
        return new BusySlot(this);
    }
}
