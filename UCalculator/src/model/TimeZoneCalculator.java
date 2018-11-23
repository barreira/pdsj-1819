package model;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class TimeZoneCalculator {

    private Set<String> timeZoneIDs;

    TimeZoneCalculator() {
        // TODO: verificar se natural orden é necessário
        timeZoneIDs = new TreeSet<>(Comparator.naturalOrder());
    }

    void initTimeZoneIDs() {
        timeZoneIDs = ZoneId.getAvailableZoneIds();
        // timeZoneIDs.forEach(System.out::println);
    }

    List<String> getMatchedTimeZoneIDs(final String id) {
        return timeZoneIDs.stream().filter(s -> s.toLowerCase().replace("_", " ").contains(id.toLowerCase())).collect(Collectors.toList());
    }
}
