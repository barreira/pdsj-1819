package view;

import java.util.List;

public class Menu {

    private final String description;
    private final List<Option> options;

    Menu(String description, List<Option> options) {
        this.description = description;
        this.options = options;
    }

    public void show() {
        System.out.println(description);
        options.forEach(System.out::println);
    }
}
