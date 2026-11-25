package ua.lpnu.kzp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Запити Stream API для обробки колекції посилок.
 * Варіант 15 — Доставлення посилок.
 */
public final class ParcelQueries {

    private ParcelQueries() {
    }

    /**
     * Відбирає посилки зі статусом "у дорозі".
     */
    public static List<Parcel> filterInTransit(List<Parcel> parcels) {
        return parcels.stream()
                .filter(p -> p.getStatus() == ParcelStatus.IN_TRANSIT)
                .toList();
    }

    /**
     * Повертає список міст доставлення.
     */
    public static List<String> mapCities(List<Parcel> parcels) {
        return parcels.stream()
                .map(Parcel::getCity)
                .toList();
    }

    /**
     * Групує посилки за містом та визначає їх кількість.
     */
    public static Map<String, Long> countByCity(List<Parcel> parcels) {
        return parcels.stream()
                .collect(Collectors.groupingBy(
                        Parcel::getCity,
                        Collectors.counting()
                ));
    }
}
