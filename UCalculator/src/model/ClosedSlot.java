package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Classe que representa um slot fechado que não permite que lhe sejam adicioanadas tarefas.
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
public class ClosedSlot extends Slot implements Serializable {

    /**
     * Inicializa um slot como um slot fechado/indisponível.
     *
     * @param id        Id do slot.
     * @param startTime Hora de início do slot.
     * @param endTime   Hora de fim do slot.
     */
    ClosedSlot(int id, LocalTime startTime, LocalTime endTime) {
        super(id, startTime, endTime);
    }

    /**
     * Construtor de cópia.
     *
     * @param closedSlot Objeto a ser copiado.
     */
    ClosedSlot(ClosedSlot closedSlot) {
        super(closedSlot);
    }

    @Override
    public String toString() {
        return "ClosedSlot{} " + super.toString();
    }

    @Override
    public ClosedSlot clone() {
        return new ClosedSlot(this);
    }
}
