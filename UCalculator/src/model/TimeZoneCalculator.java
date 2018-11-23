package model;

import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class TimeZoneCalculator {

    private Set<String> timeZoneIDs;

    TimeZoneCalculator() {
        timeZoneIDs = new TreeSet<>();
    }

    void initTimeZoneIDs() {
        timeZoneIDs = ZoneId.getAvailableZoneIds();
    }

    List<String> getMatchedTimeZoneIDs(final String id) {
        return timeZoneIDs.stream().filter(s -> s.toLowerCase().contains(id.toLowerCase()))
                .collect(Collectors.toList());
    }
}
