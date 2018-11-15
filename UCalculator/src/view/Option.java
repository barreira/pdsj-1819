package view;

class Option {

    private final String description;
    private final String key;

    Option(String description, String key) {
        this.description = description;
        this.key = key;
    }

    @Override
    public String toString() {
        return description + " " + key;
    }
}
