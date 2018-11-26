package model;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class TimeZoneCalculator {

    private Set<String> zoneIds;

    TimeZoneCalculator() {
        // TODO: verificar se natural orden é necessário
        zoneIds = new TreeSet<>(Comparator.naturalOrder());
    }

    void initTimeZoneIDs() {
        zoneIds = ZoneId.getAvailableZoneIds();
        // zoneIds.forEach(System.out::println);
    }

    List<String> getMatchedZoneIds(final String id) {
        return zoneIds.stream().filter(s -> s.toLowerCase().replace("_", " ").contains(id.toLowerCase())).collect(Collectors.toList());
    }
}
