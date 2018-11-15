package view;

import java.util.Arrays;
import java.util.Map;

import static java.util.Map.entry;

public final class UCalculatorView {

    private Map<Integer, Menu> menus;

    public UCalculatorView() {
        menus = Map.ofEntries(
                entry(0, new Menu("Main Menu", Arrays.asList(
                        new Option("Local Date Time Calculator    ", "1"),
                        new Option("Timezone Date Time Calculator ", "2"),
                        new Option("Meeting Schedule              ", "3"),
                        new Option("End Session                   ", "0")))),
                entry(1, new Menu("Local Date Time Calculator", Arrays.asList(
                        new Option("Date Time Calculator  ", "1"),
                        new Option("Weeks Calculator      ", "2"),
                        new Option("Time Units Calculator ", "3"),
                        new Option("Back                  ", "0"))))
        );
    }


    public Menu getMenu(final int key) {
        return menus.get(key);
    }
}
