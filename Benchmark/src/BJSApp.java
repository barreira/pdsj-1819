import model.BJSModel;
import model.BJSUtils;
import model.Crono;
import model.Transaction;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class BJSApp {

    private static void tests() {
        final BJSModel bjsModel = new BJSModel();
        final List<Transaction> transactions = BJSUtils.load("transCaixa1M.txt", bjsModel.parseTransaction);
        final int[] array = bjsModel.generateArrayInt(1000000);
        final Comparator<Transaction> compareTransactionsByDate = Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getId);

        System.out.println("Testing T1");
        final double[] t1_7_1 = BJSUtils.t1_7_1(transactions).get();
        final double[] t1_7_2 = BJSUtils.t1_7_2(transactions).get();
        final DoubleStream t1_8_1_1 = BJSUtils.t1_8_1_1(transactions, Transaction::getValue).get();
        final DoubleStream t1_8_1_2 = BJSUtils.t1_8_1_2(transactions, Transaction::getValue).get();
        final Stream<Double> t1_8_2_1 = BJSUtils.t1_8_2_1(transactions, Transaction::getValue).get();
        final Stream<Double> t1_8_2_2 = BJSUtils.t1_8_2_2(transactions, Transaction::getValue).get();

        System.out.println("Sizes");
        System.out.println(t1_7_1.length);
        System.out.println(t1_7_2.length);
        System.out.println(t1_8_1_1.count());
        System.out.println(t1_8_1_2.count());
        System.out.println(t1_8_2_1.count());
        System.out.println(t1_8_2_2.count());
        System.out.println("Sum");

        double sum1 = 0.0;
        double sum2 = 0.0;

        for (double d : t1_7_1) sum1 += d;
        for (double d : t1_7_2) sum2 += d;

        System.out.println(sum1);
        System.out.println(sum2);
        System.out.println(BJSUtils.t1_8_1_1(transactions, Transaction::getValue).get().sum());
        System.out.println(BJSUtils.t1_8_1_2(transactions, Transaction::getValue).get().sum());
        System.out.println(BJSUtils.t1_8_2_1(transactions, Transaction::getValue).get().mapToDouble(d -> d).sum());
        System.out.println(BJSUtils.t1_8_2_2(transactions, Transaction::getValue).get().mapToDouble(d -> d).sum());

        System.out.println("Testing T2");
        System.out.println("Sizes");
        final AbstractMap.SimpleEntry<List<Transaction>, List<Transaction>> s1 = BJSUtils.t2_list_1(transactions, 0.3, 0.4, compareTransactionsByDate).get();
        final AbstractMap.SimpleEntry<List<Transaction>, List<Transaction>> s2 = BJSUtils.t2_list_2(transactions, 0.25522, 0.4, compareTransactionsByDate).get();
        final AbstractMap.SimpleEntry<Set<Transaction>, Set<Transaction>> s3 = BJSUtils.t2_treeSet_1(transactions, 0.3, 0.4, compareTransactionsByDate).get();
        final AbstractMap.SimpleEntry<Set<Transaction>, Set<Transaction>> s4 = BJSUtils.t2_treeSet_2(transactions, 0.3, 0.4, compareTransactionsByDate).get();

        System.out.println(s1.getKey().size() + " " + s1.getValue().size());
        System.out.println(s2.getKey().size() + " " + s2.getValue().size());
        System.out.println(s3.getKey().size() + " " + s3.getValue().size());
        System.out.println(s4.getKey().size() + " " + s4.getValue().size());

        System.out.println("Testing T3");
        System.out.println("Sizes");
        System.out.println(BJSUtils.t3_IntStream(array).get().count());
        System.out.println(BJSUtils.t3(array).get().length);
        System.out.println(BJSUtils.t3(Arrays.stream(array).boxed().collect(Collectors.toList())).get().size());

        System.out.println("Testing T4");
        System.out.println(BJSUtils.t4_8_1_1(t1_7_1).get());
        System.out.println(BJSUtils.t4_8_1_2(t1_7_1).get());
        System.out.println(BJSUtils.t4_8_2_1(t1_7_1).get());
        System.out.println(BJSUtils.t4_8_2_2(t1_7_1).get());

        System.out.println("Testing T5");
        System.out.println("Sizes");
        System.out.println(BJSUtils.t5_1(transactions, compareTransactionsByDate).get().size());
        System.out.println(BJSUtils.t5_2(transactions, compareTransactionsByDate).get().size());

        System.out.println("Testing T6");
        System.out.println("Testing First Transaction of Month 1 Day 1 and Hour 1");
        System.out.println(BJSUtils.t6_7_1(transactions).get().get(Month.of(1)).get(1).get(1).get(0));
        System.out.println(BJSUtils.t6_8_1(transactions).get().get(Month.of(1)).get(1).get(1).get(0));
        System.out.println("Testing First Transaction of Monday and Hour 1");
        System.out.println(BJSUtils.t6_7_2(transactions).get().get(DayOfWeek.MONDAY).get(1).get(0));
        System.out.println(BJSUtils.t6_8_2(transactions).get().get(DayOfWeek.MONDAY).get(1).get(0));

        System.out.println("Testing T7");
        System.out.println(BJSUtils.t7_7(transactions).get());
        System.out.println(BJSUtils.t7_8_1(transactions, Transaction::getValue).get());
        System.out.println(BJSUtils.t7_8_2(transactions, Transaction::getValue).get());
        System.out.println(BJSUtils.t7_8_3(transactions, Transaction::getValue).get());

        System.out.println("Testing T8");
        System.out.println(BJSUtils.t8_7(transactions).get());
        System.out.println(BJSUtils.t8_8(transactions, bjsModel.isInRange(16, 22), Transaction::getId, Transaction::getValue).get());

        System.out.println("Testing T9");
        System.out.println("Testing for week 9");
        System.out.println(BJSUtils.t9_7(transactions, 9).get());
        System.out.println(BJSUtils.t9_8(transactions, 9).get());

        System.out.println("Testing T10");
        System.out.println("Sizes");
        System.out.println(BJSUtils.t10_7(transactions).get().size());
        System.out.println(BJSUtils.t10_8(transactions, bjsModel.getMonth, bjsModel.vat).get().size());
        System.out.println("Sum");
        System.out.println(BJSUtils.t10_7(transactions).get().stream().mapToDouble(d -> d).sum());
        System.out.println(BJSUtils.t10_8(transactions, bjsModel.getMonth, bjsModel.vat).get().stream().mapToDouble(d -> d).sum());

        System.out.println("Testing T12");
        System.out.println("Testing number of transaction for counterId 2 and Month 1");
        System.out.println(BJSUtils.t12_Map_1(transactions).get().get("2").get(Month.of(1)).size());
        System.out.println(BJSUtils.t12_ConcurrentMap_1(transactions).get().get("2").get(Month.of(1)).size());
        System.out.println("Testing sum for counterID 2");
        System.out.println(BJSUtils.t12_Map_2(BJSUtils.t12_Map_1(transactions).get()).get().get("2"));
        System.out.println(BJSUtils.t12_ConcurrentMap_2(BJSUtils.t12_ConcurrentMap_1(transactions).get()).get().get("2"));

        System.exit(0);
    }

    public static void main(String[] args) {
        //tests();
        final BJSModel model = new BJSModel();

        Crono.start();
        List<List<Transaction>> transactions = Arrays.asList(
                BJSUtils.load("../dados/transCaixa1M.txt", model.parseTransaction),
                BJSUtils.load("../dados/transCaixa2M.txt", model.parseTransaction),
                BJSUtils.load("../dados/transCaixa4M.txt", model.parseTransaction),
                BJSUtils.load("../dados/transCaixa6M.txt", model.parseTransaction));
        System.out.println("Files loaded in " + Crono.stop() + " seconds");

        model.createTimeStamp();

        System.out.println("Starting test 1");
        model.t1(transactions);
        System.out.println("Test 1 done");
        System.out.println("Starting test 2");
        model.t2(transactions);
        System.out.println("Test 2 done");
        System.out.println("Starting test 3");
        model.t3();
        System.out.println("Test 3 done");
        System.out.println("Starting test 4");
        model.t4(transactions);
        System.out.println("Test 4 done");
        System.out.println("Starting test 5");
        model.t5(transactions);
        System.out.println("Test 5 done");
        System.out.println("Starting test 6");
        model.t6(transactions);
        System.out.println("Test 6 done");
        System.out.println("Starting test 7");
        model.t7(transactions);
        System.out.println("Test 7 done");
        System.out.println("Starting test 8");
        model.t8(transactions);
        System.out.println("Test 8 done");
        System.out.println("Starting test 9");
        model.t9(transactions);
        System.out.println("Test 9 done");
        System.out.println("Starting test 10");
        model.t10(transactions);
        System.out.println("Test 10 done");
        System.out.println("Starting test 11");
        model.t11(transactions);
        System.out.println("Test 11 done");
        System.out.println("Starting test 12");
        model.t12(transactions);

        System.out.println("All done");
    }
}
