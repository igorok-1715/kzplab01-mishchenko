package ua.lpnu.kzp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Формує звіт про посилки (варіант 15). Розбір і перевірку рядків виконує {@link Parcel},
 * тут лише збираються коректні об'єкти, помилки та обчислюються показники.
 */
public final class ParcelReport {

    /* Забороняє створення екземплярів службового класу. */
    private ParcelReport() {
    }

    /**
     * Перетворює рядки на посилки, обчислює чотири показники та формує текст звіту.
     * Хибний рядок пропускається з повідомленням про номер рядка та причину.
     *
     * @param lines рядки вхідного файла
     * @return готовий текст звіту
     */
    public static String buildReport(List<String> lines) {
        List<Parcel> parcels = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int index = 0; index < lines.size(); index++) {
            try {
                parcels.add(Parcel.fromCsv(lines.get(index)));
            } catch (IllegalArgumentException e) {
                errors.add("Рядок %d: %s".formatted(index + 1, e.getMessage()));
            }
        }

        double totalWeight = 0.0;
        int maxDays = 0;
        int expressCount = 0;
        for (Parcel parcel : parcels) {
            WeightDelivery wd = parcel.toWeightDelivery();
            totalWeight += wd.weightKg();
            maxDays = Math.max(maxDays, wd.deliveryDays());
            if (parcel.isExpress()) {
                expressCount++;
            }
        }
        int validCount = parcels.size();
        double avgWeight = validCount == 0 ? 0.0 : totalWeight / validCount;
        List<Parcel> inTransit = ParcelQueries.filterInTransit(parcels);
        List<String> cities = ParcelQueries.mapCities(parcels);
        Map<String, Long> cityCounts = ParcelQueries.countByCity(parcels);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Locale.ROOT, "Коректних записів: %d%n", validCount));
        sb.append(String.format(Locale.ROOT, "Середня вага: %.2f кг%n", avgWeight));
        sb.append(String.format(Locale.ROOT, "Найбільший термін: %d дн.%n", maxDays));
        sb.append(String.format(Locale.ROOT, "Експрес-доставлень: %d%n", expressCount));
        sb.append(String.format(Locale.ROOT, "Помилок: %d%n", errors.size()));
        for (String err : errors) {
            sb.append(err).append(String.format("%n"));
        }
        if (validCount == 0) {
            sb.append(String.format("Увага: немає жодного коректного запису%n"));
        }
        sb.append("\nРезультати Stream API:\n");
        sb.append("Посилки у дорозі:\n");
        for (Parcel parcel : inTransit) {
            sb.append(parcel.getId())
            .append(" — ")
            .append(parcel.getCity())
            .append("\n");
        }
        
        sb.append("Міста доставлення: ")
        .append(cities)
        .append("\n");
        sb.append("Кількість посилок за містом:\n");
        
        cityCounts.forEach((city, count) ->
        sb.append(city)
                .append(": ")
                .append(count)
                .append("\n"));
        
        return sb.toString();
    }
}