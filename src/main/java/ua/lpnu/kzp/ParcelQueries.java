package ua.lpnu.kzp;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
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

    /**
    * Обчислює статистичні показники за вагою посилок.
    */
    public static DoubleSummaryStatistics weightStatistics(
        List<Parcel> parcels) {

    return parcels.stream()
            .mapToDouble(Parcel::getWeightKg)
            .summaryStatistics();
    }

    /**
    * Повертає N найважчих посилок.
    * За однакової ваги сортує за номером.
    */
    public static List<Parcel> topByWeight(
        List<Parcel> parcels, int n) {

    if (n < 0) {
        throw new IllegalArgumentException(
                "N не може бути від'ємним");
    }

    return parcels.stream()
            .sorted(Comparator
                    .comparingDouble(Parcel::getWeightKg)
                    .reversed()
                    .thenComparing(Parcel::getId))
            .limit(n)
            .toList();
    }
    
    /**
    * Шукає посилку за її номером.
    */
    public static Optional<Parcel> findById(
            List<Parcel> parcels, String id) {

        return parcels.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
