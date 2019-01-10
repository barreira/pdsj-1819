package model;

class DoubleWrapper {
    private double aDouble;

    DoubleWrapper() {
        aDouble = 0.0;
    }

    void add(double d) {
        aDouble += d;
    }

    double get() {
        return aDouble;
    }
}