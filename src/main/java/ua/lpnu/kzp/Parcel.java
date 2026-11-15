package ua.lpnu.kzp;

import java.util.Locale;

/**
 * Незмінна сутність «посилка». Усі правила коректності перевіряються в конструкторі,
 * тому існуючий об'єкт {@code Parcel} завжди має допустимий стан.
 */
public final class Parcel {

    private static final int FIELD_COUNT = 5;

    private final String id;
    private final double weightKg;
    private final String city;
    private final int deliveryDays;
    private final boolean express;

    /**
     * Створює посилку та перевіряє всі інваріанти.
     *
     * @param id ідентифікатор посилки (не порожній)
     * @param weightKg вага в кілограмах (скінченне число, не менше 0)
     * @param city місто доставлення (не порожнє)
     * @param deliveryDays термін доставлення в днях (не менше 0)
     * @param express ознака експрес-доставлення
     * @throws IllegalArgumentException якщо хоча б одне значення порушує правила
     */
    public Parcel(String id, double weightKg, String city, int deliveryDays, boolean express) {
        if (id == null || id.isBlank() || city == null || city.isBlank()) {
            throw new IllegalArgumentException("порожній id або місто");
        }
        if (!Double.isFinite(weightKg)) {
            throw new IllegalArgumentException("числове поле має помилковий формат");
        }
        if (weightKg < 0 || deliveryDays < 0) {
            throw new IllegalArgumentException("від'ємне числове значення");
        }
        this.id = id;
        this.weightKg = weightKg;
        this.city = city;
        this.deliveryDays = deliveryDays;
        this.express = express;
    }

    /**
     * Створює посилку з одного CSV-рядка формату {@code id;weightKg;city;deliveryDays;express}.
     * Метод розбирає структуру й типи, а правила предметної області перевіряє конструктор.
     *
     * @param line рядок CSV
     * @return коректна посилка
     * @throws IllegalArgumentException якщо рядок порожній, має хибну структуру
     *     чи значення, що не проходять перевірку
     */
    public static Parcel fromCsv(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("порожній рядок");
        }
        String[] f = line.split(";", -1);
        if (f.length != FIELD_COUNT) {
            throw new IllegalArgumentException(
                    "очікується %d полів, отримано %d".formatted(FIELD_COUNT, f.length));
        }
        if (!"true".equals(f[4]) && !"false".equals(f[4])) {
            throw new IllegalArgumentException("express має бути true або false");
        }
        try {
            double weight = Double.parseDouble(f[1]);
            int days = Integer.parseInt(f[3]);
            return new Parcel(f[0], weight, f[2], days, Boolean.parseBoolean(f[4]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("числове поле має помилковий формат", e);
        }
    }

    /**
     * Повертає вагу й термін посилки як незмінне значення.
     *
     * @return значення {@link WeightDelivery}
     */
    public WeightDelivery toWeightDelivery() {
        return new WeightDelivery(weightKg, deliveryDays);
    }

    /**
     * Повертає ідентифікатор.
     *
     * @return id посилки
     */
    public String getId() {
        return id;
    }

    /**
     * Повертає вагу.
     *
     * @return вага в кілограмах
     */
    public double getWeightKg() {
        return weightKg;
    }

    /**
     * Повертає місто.
     *
     * @return місто доставлення
     */
    public String getCity() {
        return city;
    }

    /**
     * Повертає термін доставлення.
     *
     * @return кількість днів
     */
    public int getDeliveryDays() {
        return deliveryDays;
    }

    /**
     * Повідомляє, чи доставлення експрес.
     *
     * @return {@code true} для експрес-доставлення
     */
    public boolean isExpress() {
        return express;
    }

    /**
     * Текстове представлення; дробове число форматується незалежно від локалі.
     *
     * @return рядок виду {@code Parcel{id=P001, weightKg=2.50, ...}}
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "Parcel{id=%s, weightKg=%.2f, city=%s, deliveryDays=%d, express=%b}",
                id, weightKg, city, deliveryDays, express);
    }
}