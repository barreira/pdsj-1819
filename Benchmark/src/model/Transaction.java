package model;

import java.time.LocalDateTime;


public class Transaction {
    private final String id;
    private final String counterId;
    private final double value;
    private final LocalDateTime date;

    static Transaction of(String id, String counterId, double value, LocalDateTime date) {
        return new Transaction(id, counterId, value, date);
    }

    private Transaction(String id, String counterId, double value, LocalDateTime date) {
        this.id = id;
        this.counterId = counterId;
        this.value = value;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getCounterId() {
        return counterId;
    }

    public double getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "model.Transaction{" +
                "id='" + id + '\'' +
                ", counterId='" + counterId + '\'' +
                ", value=" + value +
                ", date=" + date +
                '}';
    }
}
