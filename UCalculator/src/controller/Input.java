/*
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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Input {

    static LocalDate readDate(DateTimeFormatter formatter) {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        boolean ok = false;
        LocalDate localDate = LocalDate.now();

        while (!ok) {
            try {
                localDate = LocalDate.parse(bufferedReader.readLine(), formatter);
                ok = true;
            } catch (IOException | DateTimeParseException e) {
                System.out.println("Insert new value: ");
            }
        }

        return localDate;
    }

    public static LocalDateTime readDateTime(DateTimeFormatter formatter) {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            return LocalDateTime.parse(bufferedReader.readLine(), formatter);
        } catch (IOException | DateTimeParseException e) {
            return null;
        }
    }

    static int readInt() {
        final Scanner input = new Scanner(System.in);
        boolean ok = false;
        int i = 0;

        while (!ok) {
            try {
                i = input.nextInt();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.println("Insert new value: ");
            }
        }

        return i;
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
