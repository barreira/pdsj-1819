package model;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Classe que possui informação relativa aos identificadores de zona (zone IDs) utilizados na calculdadora de TimeZones
 *
 * @author Diogo Silva
 * @author João Barreira
 * @author Rafael Braga
 */
class TimeZoneCalculator {

    private Set<String> zoneIds; // identificadores de zona (zone IDs)

    /**
     * Construtor vazio.
     */
    TimeZoneCalculator() {
        zoneIds = new TreeSet<>(Comparator.naturalOrder());
    }

    /**
     * Coloca na variável de instância zoneIds, todos os identificadores de zona (zone IDs) disponíveis.
     */
    void initTimeZoneIDs() {
        zoneIds = ZoneId.getAvailableZoneIds();
    }

    /**
     * Dado um identificador de zona (zone ID), devolve todos os identificadores da zona correspondentes.
     *
     * Para "Africa" devolve uma lista com todos os identificadores de cidades africanas (e.g. Africa/Abidjan,
     * Africa/Accra, etc.). Para "Lisbon", devolve uma lista apenas com o identificador de zona "Europe/Lisbon".
     *
     * @param id Identificador de zona
     *
     * @return List<String> Lista com os identificadores de zona (zone IDs) correspondentes
     */
    List<String> getMatchedZoneIds(final String id) {
        return zoneIds.stream().filter(s -> s.toLowerCase().replace("_", " ")
                .contains(id.toLowerCase())).collect(Collectors.toList());
    }
}
