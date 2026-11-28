
package ua.lpnu.kzp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Формує звіт про посилки (варіант 15).
 * Розбір і перевірку рядків виконує {@link Parcel}.
 * Обчислення контрольних показників виконуються через Stream API.
 */
public final class ParcelReport {

    /* Забороняє створення екземплярів службового класу. */
    private ParcelReport() {
    }

    /**
     * Перетворює рядки на посилки, обчислює показники
     * через Stream API та формує текст звіту.
     * Хибний рядок пропускається з повідомленням
     * про номер рядка та причину.
     *
     * @param lines рядки вхідного файла
     * @return готовий текст звіту
     */
    public static String buildReport(List<String> lines) {
        List<Parcel> parcels = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        IntStream.range(0, lines.size()).forEachOrdered(index -> {
            try {
                parcels.add(Parcel.fromCsv(lines.get(index)));
            } catch (IllegalArgumentException e) {
                errors.add("Рядок %d: %s"
                        .formatted(index + 1, e.getMessage()));
            }
        });

        // Обчислення статистики за вагою через Stream API
        DoubleSummaryStatistics stats =
                ParcelQueries.weightStatistics(parcels);

        int validCount = parcels.size();

        double avgWeight = stats.getAverage();

        // Визначення найбільшого терміну доставлення
        int maxDays = parcels.stream()
                .map(Parcel::toWeightDelivery)
                .mapToInt(WeightDelivery::deliveryDays)
                .max()
                .orElse(0);

        // Підрахунок експрес-доставлень
        long expressCount = parcels.stream()
                .filter(Parcel::isExpress)
                .count();

        // Основні запити Stream API
        List<Parcel> inTransit =
                ParcelQueries.filterInTransit(parcels);

        List<String> cities =
                ParcelQueries.mapCities(parcels);

        Map<String, Long> cityCounts =
                ParcelQueries.countByCity(parcels);

        List<Parcel> top5 =
                ParcelQueries.topByWeight(parcels, 5);

        Optional<Parcel> foundParcel =
                ParcelQueries.findById(parcels, "P001");

        Map<ParcelStatus, Map<String, Double>> groupedWeights =
                ParcelQueries.groupWeightByStatusAndCity(parcels);

        StringBuilder sb = new StringBuilder();

        sb.append(String.format(Locale.ROOT,
                "Коректних записів: %d%n", validCount));

        sb.append(String.format(Locale.ROOT,
                "Середня вага: %.2f кг%n", avgWeight));

        sb.append(String.format(Locale.ROOT,
                "Найбільший термін: %d дн.%n", maxDays));

        sb.append(String.format(Locale.ROOT,
                "Експрес-доставлень: %d%n", expressCount));

        sb.append(String.format(Locale.ROOT,
                "Помилок: %d%n", errors.size()));

        errors.forEach(err ->
                sb.append(err).append(System.lineSeparator()));

        if (validCount == 0) {
            sb.append(String.format(
                    "Увага: немає жодного коректного запису%n"));
        }

        sb.append("\nРезультати Stream API:\n");

        sb.append("Посилки у дорозі:\n");

        inTransit.stream()
                .map(parcel -> parcel.getId()
                        + " — " + parcel.getCity() + "\n")
                .forEachOrdered(sb::append);

        sb.append("Міста доставлення: ")
                .append(cities)
                .append("\n");

        sb.append("Кількість посилок за містом:\n");

        cityCounts.forEach((city, count) ->
                sb.append(city)
                        .append(": ")
                        .append(count)
                        .append("\n"));

        sb.append("\nСтатистика за вагою посилок:\n");

        sb.append(String.format(Locale.ROOT,
                "Кількість: %d%n", stats.getCount()));

        sb.append(String.format(Locale.ROOT,
                "Сумарна вага: %.2f кг%n", stats.getSum()));

        sb.append(String.format(Locale.ROOT,
                "Мінімальна вага: %.2f кг%n",
                stats.getCount() == 0 ? 0.0 : stats.getMin()));

        sb.append(String.format(Locale.ROOT,
                "Максимальна вага: %.2f кг%n",
                stats.getCount() == 0 ? 0.0 : stats.getMax()));

        sb.append(String.format(Locale.ROOT,
                "Середня вага: %.2f кг%n", stats.getAverage()));

        sb.append("\nTop-5 посилок за вагою:\n");

        top5.stream()
                .map(parcel -> String.format(Locale.ROOT,
                        "%s — %s — %.2f кг%n",
                        parcel.getId(),
                        parcel.getCity(),
                        parcel.getWeightKg()))
                .forEachOrdered(sb::append);

        sb.append("\nПошук посилки за номером P001:\n");

        foundParcel.ifPresentOrElse(
                parcel -> sb.append("Посилку знайдено: ")
                        .append(parcel.getId())
                        .append(" — ")
                        .append(parcel.getCity())
                        .append("\n"),
                () -> sb.append("Посилку не знайдено.\n")
        );

        sb.append("\nДворівневе групування посилок:\n");

        groupedWeights.forEach((status, cityWeights) -> {
            sb.append("Статус: ")
                    .append(status)
                    .append("\n");

            cityWeights.forEach((city, weight) ->
                    sb.append(String.format(Locale.ROOT,
                            "  %s: %.2f кг%n", city, weight)));
        });

        return sb.toString();
    }
}
