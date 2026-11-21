package ua.lpnu.kzp;

import java.util.Locale;
import java.util.Objects;

/**
 * Базовий клас посилки.
 * Підтримує звичайне та експрес-доставлення.
 */
public sealed class Parcel
        permits RegularParcel, ExpressParcel {

    private static final int FIELD_COUNT = 5;

    private final String id;
    private final double weightKg;
    private final String city;
    private final int deliveryDays;
    private final boolean express;

    /**
     * Створює посилку та перевіряє коректність даних.
     */
    public Parcel(String id, double weightKg, String city,
                  int deliveryDays, boolean express) {

        if (id == null || id.isBlank()
                || city == null || city.isBlank()) {
            throw new IllegalArgumentException(
                    "порожній id або місто");
        }

        if (!Double.isFinite(weightKg)) {
            throw new IllegalArgumentException(
                    "числове поле має помилковий формат");
        }

        if (weightKg < 0 || deliveryDays < 0) {
            throw new IllegalArgumentException(
                    "від'ємне числове значення");
        }

        this.id = id;
        this.weightKg = weightKg;
        this.city = city;
        this.deliveryDays = deliveryDays;
        this.express = express;
    }

    /**
     * Захист від перевизначення фіналізатора підтипами.
     */
    @Override
    @SuppressWarnings("deprecation")
    protected final void finalize() {
        // Фіналізація не використовується.
    }

    /**
     * Перетворює CSV-рядок на відповідний підтип Parcel.
     */
    public static Parcel fromCsv(String line) {

        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("порожній рядок");
        }

        String[] f = line.split(";", -1);

        if (f.length != FIELD_COUNT) {
            throw new IllegalArgumentException(
                    "очікується %d полів, отримано %d"
                            .formatted(FIELD_COUNT, f.length));
        }

        if (!"true".equals(f[4]) && !"false".equals(f[4])) {
            throw new IllegalArgumentException(
                    "express має бути true або false");
        }

        try {
            double weight = Double.parseDouble(f[1]);
            int days = Integer.parseInt(f[3]);

            if (Boolean.parseBoolean(f[4])) {
                return new ExpressParcel(
                        f[0], weight, f[2], days);
            }

            return new RegularParcel(
                    f[0], weight, f[2], days);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "числове поле має помилковий формат", e);
        }
    }

    /**
     * Повертає вагу та термін доставлення.
     */
    public WeightDelivery toWeightDelivery() {
        return new WeightDelivery(weightKg, deliveryDays);
    }

    public String getId() {
        return id;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public String getCity() {
        return city;
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public boolean isExpress() {
        return express;
    }

    /**
     * Повертає вид доставлення.
     */
    public DeliveryKind getDeliveryKind() {
        return express
                ? DeliveryKind.EXPRESS
                : DeliveryKind.REGULAR;
    }

    /**
     * Розраховує термін доставлення.
     */
    public int calculateDeliveryTime() {
        return deliveryDays;
    }

    /**
     * Розраховує вартість доставлення.
     */
    public double calculateDeliveryCost() {
        return express
                ? 100.0 + weightKg * 20.0
                : 50.0 + weightKg * 10.0;
    }

    /**
     * Перевіряє логічну рівність посилок.
     *
     * @param obj об'єкт для порівняння
     * @return true, якщо об'єкти логічно рівні
     */
    @Override
    public final boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Parcel other = (Parcel) obj;

        return Double.compare(weightKg, other.weightKg) == 0
                && deliveryDays == other.deliveryDays
                && express == other.express
                && id.equals(other.id)
                && city.equals(other.city);
    }

    /**
     * Обчислює хеш-код посилки.
     * Використовує ті самі поля, що й equals().
     *
     * @return хеш-код об'єкта
     */
    @Override
    public final int hashCode() {
        return Objects.hash(
                getClass(),
                id,
                weightKg,
                city,
                deliveryDays,
                express
        );
    }

    /**
     * Текстове представлення посилки.
     */
    @Override
    public String toString() {
        return String.format(
                Locale.ROOT,
                "Parcel{id=%s, weightKg=%.2f, city=%s, deliveryDays=%d, express=%b}",
                id, weightKg, city, deliveryDays, express);
    }

    /**
     * Створює Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder для створення посилки.
     */
    public static final class Builder {

        private String id;
        private Double weightKg;
        private String city;
        private Integer deliveryDays;
        private boolean express;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder weightKg(double weightKg) {
            this.weightKg = weightKg;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder deliveryDays(int deliveryDays) {
            this.deliveryDays = deliveryDays;
            return this;
        }

        public Builder express(boolean express) {
            this.express = express;
            return this;
        }

        public Parcel build() {
            if (weightKg == null || deliveryDays == null) {
                throw new IllegalArgumentException(
                        "Не вказано вагу або термін доставки");
            }

            if (express) {
                return new ExpressParcel(
                        id, weightKg, city, deliveryDays);
            }

            return new RegularParcel(
                    id, weightKg, city, deliveryDays);
        }
    }
}