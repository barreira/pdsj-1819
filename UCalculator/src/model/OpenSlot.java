package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Classe que sinaliza um slot aberto/disponível.
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
public class OpenSlot extends Slot implements Serializable {

    /**
     * Inicializa um slot como um slot aberto.
     *
     * @param id        Id do slot.
     * @param startTime Hora de início do slot.
     * @param endTime   Hora de fim do slot.
     */
    OpenSlot(int id, LocalTime startTime, LocalTime endTime) {
        super(id, startTime, endTime);
    }

    /**
     * Construtor de cópia.
     *
     * @param openSlot Objeto a ser copiado.
     */
    OpenSlot(OpenSlot openSlot) {
        super(openSlot);
    }

    @Override
    public String toString() {
        return "OpenSlot{} " + super.toString();
    }

    @Override
    public OpenSlot clone() {
        return new OpenSlot(this);
    }
}
