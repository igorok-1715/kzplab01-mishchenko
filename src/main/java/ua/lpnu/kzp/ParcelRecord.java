package ua.lpnu.kzp;

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
}