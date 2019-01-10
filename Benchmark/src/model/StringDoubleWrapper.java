package model;

class StringDoubleWrapper {
    private String id;
    private double value;

    StringDoubleWrapper() {
        id = "";
        value = 0.0;
    }

    void set(String id, double value) {
        this.id = id;
        this.value = value;
    }

    double getValue() {
        return value;
    }

    String getId() {
        return id;
    }
}