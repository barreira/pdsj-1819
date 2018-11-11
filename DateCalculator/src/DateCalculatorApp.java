import model.Facade;

import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.TemporalAmount;

public class DateCalculatorApp {

    public static void main(String[] args) {
        Facade f = new Facade();

        Period p = Period.ofWeeks(5);

        System.out.println(p.toString());
    }
}
