/**
 * Classe que abstrai a utilização da classe Scanner, escondendo todos os
 * problemas relacionados com excepções, e que oferece métodos simples e
 * robustos para a leitura de valores de tipos simples e String.
 *
 * É uma classe de serviços, como Math e outras de Java, pelo que devem ser
 * usados os métodos de classe implementados.
 *
 * Utilizável em BlueJ, NetBeans, CodeBlocks ou Eclipse.
 *
 * Utilização típica:  int x = Input.lerInt();
 *                     String nome = Input.lerString();
 *
 * @author F. Mário Martins
 * @version 1.0 (6/2006)
 */
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class Input {

    public static LocalDate readDate(DateTimeFormatter formatter) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            return LocalDate.parse(bufferedReader.readLine(), formatter);
        } catch (IOException | DateTimeParseException e) {
            return null;
        }
    }


    public static LocalDateTime readDateTime(DateTimeFormatter formatter) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            return LocalDateTime.parse(bufferedReader.readLine(), formatter);
        } catch (IOException | DateTimeParseException e) {
            return null;
        }
    }


    static Period readPeriod() {
        return null;
    }


    static String readString() {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean ok = false;
        String str = "";

        while (!ok) {
            try {
                str = bufferedReader.readLine();
                ok = true;
            } catch (IOException e) {
                System.out.println("Insert new value: ");
            }
        }

        return str;
    }
}
