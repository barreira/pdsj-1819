package model;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BJSModel {
    private final static int MIN = 1, MAX = 9999;

    private String timeStamp;

    public BJSModel() {
        this.createTimeStamp();
    }

    public void createTimeStamp() {
        timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"));
    }

    public void t1(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "J7 for", "J7 forEach", "J8 DoubleStream Sequential",
                "J8 DoubleStream Parallel", "J8 Stream<Double> Sequential",
                "J8 Stream<Double> Parallel"));

        transactions.forEach(t -> tests.add(Arrays.asList(
                t.size(), BJSUtils.testBox(BJSUtils.t1_7_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t1_7_2(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t1_8_1_1(t, Transaction::getValue)).getKey(),
                BJSUtils.testBox(BJSUtils.t1_8_1_2(t, Transaction::getValue)).getKey(),
                BJSUtils.testBox(BJSUtils.t1_8_2_1(t, Transaction::getValue)).getKey(),
                BJSUtils.testBox(BJSUtils.t1_8_2_2(t, Transaction::getValue)).getKey())
                )
        );


        BJSUtils.write("t1_" + timeStamp + ".csv", tests);
    }

    public void t2(final List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "List Sequential", "List Parallel", "TreeSet Sequential", "TreeSet Parallel"));

        transactions.forEach(t -> tests.add(Arrays.asList(
                t.size(), BJSUtils.testBox(BJSUtils.t2_list_1(t, 0.3, 0.3, this.compareTransactionsByDate)).getKey(),
                BJSUtils.testBox(BJSUtils.t2_list_2(t, 0.3, 0.3, this.compareTransactionsByDate)).getKey(),
                BJSUtils.testBox(BJSUtils.t2_treeSet_1(t, 0.3, 0.3, this.compareTransactionsByDate)).getKey(),
                BJSUtils.testBox(BJSUtils.t2_treeSet_2(t, 0.3, 0.3, this.compareTransactionsByDate)).getKey()
                )
        ));

        BJSUtils.write("t2_" + timeStamp + ".csv", tests);
    }

    public void t3() {
        final List<int[]> ints = new ArrayList<>();
        final List<List<Object>> tests = new ArrayList<>();
        IntStream.range(1, 9).forEach(i -> ints.add(this.generateArrayInt(i * 1000000)));

        tests.add(Arrays.asList("", "IntStream", "int[]", "List<Integer>"));

        ints.forEach(t -> tests.add(Arrays.asList(
                t.length, BJSUtils.testBox(BJSUtils.t3_IntStream(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t3(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t3(this.toList(t))).getKey()
        )));

        BJSUtils.write("t3_" + timeStamp + ".csv", tests);
    }

    public void t4(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();
        final List<double[]> values = new ArrayList<>();

        tests.add(Arrays.asList("", "BiFunction Sequential", "BiFunction Parallel", "Lambda Sequential",
                "Lambda Parallel"));

        transactions.forEach(t -> values.add(BJSUtils.t1_7_2(t).get()));

        values.forEach(t -> tests.add(Arrays.asList(
                t.length, BJSUtils.testBox(BJSUtils.t4_8_1_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t4_8_1_2(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t4_8_2_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t4_8_2_2(t)).getKey()
        )));

        BJSUtils.write("t4_" + timeStamp + ".csv", tests);
    }

    public void t5(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "TreeSet", "List"));

        transactions.forEach(t -> tests.add(Arrays.asList(
                t.size(), BJSUtils.testBox(BJSUtils.t5_1(t, this.compareTransactionsByDate)).getKey(),
                BJSUtils.testBox(BJSUtils.t5_2(t, this.compareTransactionsByDate)).getKey())
        ));

        BJSUtils.write("t5_" + timeStamp + ".csv", tests);
    }

    public void t6(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "J7 Map<Month Map<Day Map<Hour List>>>", "J7 Map<DayOfWeek Map<Hour List>>",
                "J8 Map<Month Map<Day Map<Hour List>>>", "J8 Map<DayOfWeek Map<Hour List>>"));

        transactions.forEach(t -> tests.add(Arrays.asList(t.size(),
                BJSUtils.testBox(BJSUtils.t6_7_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t6_7_2(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t6_8_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t6_8_2(t)).getKey()
        )));

        BJSUtils.write("t6_" + timeStamp + ".csv", tests);
    }

    public void t7(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "J7 List", "J8 Spliterator", "J8 Sequential", "J8 Parallel"));

        transactions.forEach(t -> tests.add(Arrays.asList(t.size(),
                BJSUtils.testBox(BJSUtils.t7_7(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t7_8_1(t, Transaction::getValue)).getKey(),
                BJSUtils.testBox(BJSUtils.t7_8_2(t, Transaction::getValue)).getKey(),
                BJSUtils.testBox(BJSUtils.t7_8_3(t, Transaction::getValue)).getKey())
        ));

        BJSUtils.write("t7_" + timeStamp + ".csv", tests);
    }

    public void t8(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "J7", "J8"));

        transactions.forEach(t -> tests.add(Arrays.asList(t.size(),
                BJSUtils.testBox(BJSUtils.t8_7(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t8_8(t, isInRange(16, 22), Transaction::getId, Transaction::getValue)).getKey())
        ));

        BJSUtils.write("t8_" + timeStamp + ".csv", tests);
    }

    public void t9(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "J7", "J8"));

        transactions.forEach(t -> tests.add(Arrays.asList(t.size(),
                BJSUtils.testBox(BJSUtils.t9_7(t, 24)).getKey(),
                BJSUtils.testBox(BJSUtils.t9_8(t, 24)).getKey())
        ));

        BJSUtils.write("t9_" + timeStamp + ".csv", tests);
    }

    public void t10(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "J7", "J8"));

        transactions.forEach(t -> tests.add(Arrays.asList(t.size(),
                BJSUtils.testBox(BJSUtils.t10_7(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t10_8(t, getMonth, vat)).getKey())
        ));

        BJSUtils.write("t10_" + timeStamp + ".csv", tests);
    }

    public void t11(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "T1", "T6", "T8", "T10"));

        transactions.forEach(t -> tests.add(Arrays.asList(t.size(),
                BJSUtils.testBox(BJSUtils.t1_8_1_1(t, Transaction::getValue)).getKey(),
                BJSUtils.testBox(BJSUtils.t6_8_1(t)).getKey(),
                BJSUtils.testBox(BJSUtils.t8_8(t, isInRange(16, 22), Transaction::getId, Transaction::getValue)).getKey(),
                BJSUtils.testBox(BJSUtils.t10_8(t, getMonth, vat)).getKey())
        ));

        BJSUtils.write("t11_" + timeStamp + ".csv", tests);
    }

    public void t12(List<List<Transaction>> transactions) {
        final List<List<Object>> tests = new ArrayList<>();

        tests.add(Arrays.asList("", "Map<CounterId Map<Month List>>", "ConcurrentMap<CounterId ConcurrentMap<Month List>>",
                "Map<CounterId Double>", "ConcurrentMap<CounterId Double"));

        transactions.forEach(t -> {
            final Map<String, Map<Month, List<Transaction>>> map = BJSUtils.t12_Map_1(t).get();
            final ConcurrentMap<String, ConcurrentMap<Month, List<Transaction>>> concurrentMap = BJSUtils.t12_ConcurrentMap_1(t).get();

            tests.add(Arrays.asList(t.size(),
                    BJSUtils.testBox(BJSUtils.t12_Map_1(t)).getKey(),
                    BJSUtils.testBox(BJSUtils.t12_ConcurrentMap_1(t)).getKey(),
                    BJSUtils.testBox(BJSUtils.t12_Map_2(map)).getKey(),
                    BJSUtils.testBox(BJSUtils.t12_ConcurrentMap_2(concurrentMap)).getKey()
            ));
        });

        BJSUtils.write("t12_" + timeStamp + ".csv", tests);
    }

    public int[] generateArrayInt(final int size) {
        return generateArrayInt(MIN, MAX, size);
    }

    public int[] generateArrayInt(final int min, final int max, final int size) {
        return new Random().ints(size, min, max + 1).toArray();
    }

    private List<Integer> toList(final int[] array) {
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    private final Comparator<Transaction> compareTransactionsByDate = Comparator.comparing(Transaction::getDate)
            .thenComparing(Transaction::getId);

    public Function<String, Transaction> parseTransaction = (line) -> {
        final String[] fields = line.split("/");
        final String id = fields[0].trim();
        final String counterId = fields[1].trim();
        final double value;

        try {
            value = Double.parseDouble(fields[2]);
        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        final String[] dMYHMS = fields[3].split("T");

        final String[] dMY = dMYHMS[0].split(":");
        final String[] hM = dMYHMS[1].split(":");

        final int year;
        final int month;
        final int day;
        final int hours;
        final int minutes;

        try {
            day = Integer.parseInt(dMY[0]);
            month = Integer.parseInt(dMY[1]);
            year = Integer.parseInt(dMY[2]);
            hours = Integer.parseInt(hM[0]);
            minutes = Integer.parseInt(hM[1]);
        } catch (InputMismatchException | NumberFormatException e) {
            return null;
        }

        return Transaction.of(id, counterId, value, LocalDateTime.of(year, month, day, hours, minutes, 0));
    };

    public Predicate<Transaction> isInRange(final int begin, final int end) {
        return t -> t.getDate().getHour() >= begin && t.getDate().getHour() < end;
    }

    public Function<Transaction, Integer> getMonth = t -> t.getDate().getMonth().getValue();

    public Function<Transaction, Double> vat = t -> {
        if(t.getValue() > 29) {
            return t.getValue() * 0.23;
        } else if(t.getValue() < 20) {
            return t.getValue() * 0.12;
        } else {
            return t.getValue() * 0.20;
        }
    };
}

