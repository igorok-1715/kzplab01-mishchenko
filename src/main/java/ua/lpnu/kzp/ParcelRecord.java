package ua.lpnu.kzp;

import java.util.List;

/**
 * Предметна модель доставлення посилок.
 * Варіант №15.
 */
public record ParcelRecord(

        @CsvColumn(name = "Номер", order = 1)
        String trackingNumber,

        @CsvColumn(name = "Вага", order = 2)
        double weightKg,

        @CsvColumn(name = "Габарити", order = 3)
        String dimensions,

        @CsvColumn(name = "Місто", order = 4)
        String city,

        @CsvColumn(name = "Статус", order = 5)
        ParcelStatus status

) {

    /**
     * Створює посилку з CSV-запису.
     */
    public static ParcelRecord fromCsv(String line) {
        return fromCsvFields(CsvParser.parseLine(line));
    }

    /**
     * Створює посилку зі списку CSV-полів.
     */
    public static ParcelRecord fromCsvFields(
            List<String> fields) {

        if (fields == null || fields.size() != 5) {
            throw new DataStorageException(
                    "Очікується 5 CSV-полів");
        }

        try {

            if (fields.stream().anyMatch(value -> value == null)) {
                throw new IllegalArgumentException(
                        "CSV-поля не можуть містити null");
            }

            String trackingNumber = fields.get(0);
            double weightKg = Double.parseDouble(fields.get(1));
            String dimensions = fields.get(2);
            String city = fields.get(3);

            ParcelStatus status =
                    ParcelStatus.valueOf(fields.get(4));

            if (trackingNumber.isBlank()
                    || dimensions.isBlank()
                    || city.isBlank()) {
                throw new IllegalArgumentException(
                        "Обов'язкові поля не можуть бути порожніми");
            }

            if (!Double.isFinite(weightKg) || weightKg <= 0) {
                throw new IllegalArgumentException(
                        "Вага повинна бути додатним скінченним числом");
            }

            return new ParcelRecord(
                    trackingNumber,
                    weightKg,
                    dimensions,
                    city,
                    status
            );

        } catch (IllegalArgumentException e) {
            throw new DataStorageException(
                    "Помилка відновлення посилки з CSV", e);
        }
    }
}