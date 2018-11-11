package model;

import java.time.LocalDateTime;

public class Operand extends Operation {

    private LocalDateTime operand;

    @Override
    Result execute() {
        return new LocalDateTimeWrapper(operand);
    }
}
