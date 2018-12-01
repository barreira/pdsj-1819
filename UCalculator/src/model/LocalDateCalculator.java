package model;

import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.AbstractMap.SimpleEntry;

/**
 * Classe que possui stack de datas relativas às operações da calculadora de LocalDates
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
class LocalDateCalculator {

    // State of operation results to backtrack
    private Stack<LocalDate> stack;

    /**
     * Construtor vazio.
     */
    LocalDateCalculator() {
        stack = new Stack<>();
    }

    /**
     * Coloca na stack a LocalDate recebida.
     *
     * @param LocalDate LocalDate a colocar na stack
     */
    void push(LocalDate LocalDate) {
        stack.push(LocalDate);
    }

    /**
     * Coloca na stack o resultado (LocalDate) da aplicação da BiFunction e respetivos argumentos (duration e
     * temporalUnit) ao elemento (LocalDate) que estava no topo da stack.
     *
     * @param biFunction   Função a aplicar à LocalDate do topo da stack
     * @param duration     Número de unidades temporais (primeiro elemento do argumento a ser passado à BiFunction
     *                     recebida)
     * @param temporalUnit Unidade temporal (segundo elemento do argumento a ser passado à BiFunction recebida)
     */
    void push(BiFunction<LocalDate, SimpleEntry<Integer, TemporalUnit>, LocalDate> biFunction, int duration,
              TemporalUnit temporalUnit) {
        stack.push(biFunction.apply(stack.peek(), new SimpleEntry<>(duration, temporalUnit)));
    }

    /**
     * Coloca na stack o resultado (LocalDate) da aplicação da BiFunction e respetivo argumento ao elemento (LocalDate)
     * que estava no topo da stack.
     *
     * @param biFunction Função a aplicar à LocalDate do topo da stack
     * @param argument   Argumento a ser passado à BiFunction recebida
     */
    void push(BiFunction<LocalDate, Integer, LocalDate> biFunction, int argument) {
        stack.push(biFunction.apply(stack.peek(), argument));
    }

    /**
     * Retira o elemento do topo da stack.
     */
    void pop() {
        stack.pop();
    }

    /**
     * Devolve a LocalDate do topo da stack sem a remover da mesma.
     *
     * @return LocalDate LocalDate do topo da stack
     */
    LocalDate peek() {
        return stack.peek();
    }

    /**
     * Limpa a stack.
     */
    void clear() {
        stack.clear();
    }
}
