package ua.lpnu.kzp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Перевірка записів про доставлення посилок (варіант 15) і формування звіту.
 * Формат рядка: id;weightKg;city;deliveryDays;express.
 */
public final class ParcelReport {

    /* Забороняє створення екземплярів службового класу. */
    private ParcelReport() {
    }

    /**
     * Читає й перевіряє записи, обчислює чотири показники та формує текст звіту.
     * Хибні рядки пропускаються з повідомленням про номер рядка та причину.
     *
     * @param lines рядки вхідного файла
     * @return готовий текст звіту
     */
    public static String buildReport(List<String> lines) {
        List<String> errors = new ArrayList<>();
        int validCount = 0;
        double totalWeight = 0.0;
        int maxDays = 0;
        int expressCount = 0;

        for (int index = 0; index < lines.size(); index++) {
            int n = index + 1;
            String line = lines.get(index);

            if (line.isBlank()) {
                errors.add("Рядок %d: порожній рядок".formatted(n));
                continue;
            }
            String[] f = line.split(";", -1);
            if (f.length != 5) {
                errors.add("Рядок %d: очікується 5 полів, отримано %d".formatted(n, f.length));
                continue;
            }
            if (f[0].isBlank() || f[2].isBlank()) {
                errors.add("Рядок %d: порожній id або місто".formatted(n));
                continue;
            }
            if (!"true".equals(f[4]) && !"false".equals(f[4])) {
                errors.add("Рядок %d: express має бути true або false".formatted(n));
                continue;
            }
            try {
                double weight = Double.parseDouble(f[1]);
                int days = Integer.parseInt(f[3]);
                if (weight < 0 || days < 0) {
                    errors.add("Рядок %d: від'ємне числове значення".formatted(n));
                    continue;
                }
                validCount++;
                totalWeight += weight;
                maxDays = Math.max(maxDays, days);
                if ("true".equals(f[4])) {
                    expressCount++;
                }
            } catch (NumberFormatException e) {
                errors.add("Рядок %d: числове поле має помилковий формат".formatted(n));
            }
        }

        double avgWeight = validCount == 0 ? 0.0 : totalWeight / validCount;

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
        return sb.toString();
    }
}
