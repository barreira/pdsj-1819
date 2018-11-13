import model.*;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.TemporalAmount;

public class DateCalculatorApp {

    public static void main(String[] args) {
        Facade f = new Facade();


        AST a = new AST();

        a.add(new LocalDateTimeElement(LocalDateTime.now()));
        a.add(new AdditionElement());
        a.add(new LocalDateTimeElement(LocalDateTime.now().plusDays(10)));

        Result r = a.calculate();

        System.out.println(r.toString());
    }
}
