package ua.lpnu.kzp;
import java.util.Locale;
import java.util.Objects;

/**
 * Базовий клас посилки.
 * Підтримує звичайне та експрес-доставлення.
 */
public abstract sealed class Parcel
        permits RegularParcel, ExpressParcel {

    private static final int FIELD_COUNT = 5;
    private static final int EXTENDED_FIELD_COUNT = 7;

    private final String id;
    private final double weightKg;
    private final String city;
    private final int deliveryDays;
    private final boolean express;
    private final String dimensions;
    private final ParcelStatus status;

    /**
     * Створює посилку зі старим набором полів.
     */
    protected Parcel(String id, double weightKg, String city,
                     int deliveryDays, boolean express) {

        this(id, weightKg, city, deliveryDays, express,
                "UNKNOWN", ParcelStatus.UNKNOWN);
    }

    /**
     * Створює посилку з габаритами та статусом.
     */
    protected Parcel(String id, double weightKg, String city,
                     int deliveryDays, boolean express,
                     String dimensions, ParcelStatus status) {

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

        if (dimensions == null || dimensions.isBlank()) {
            throw new IllegalArgumentException(
                    "Не вказано габарити");
        }

        if (status == null) {
            throw new IllegalArgumentException(
                    "Не вказано статус");
        }

        this.id = id;
        this.weightKg = weightKg;
        this.city = city;
        this.deliveryDays = deliveryDays;
        this.express = express;
        this.dimensions = dimensions;
        this.status = status;
    }

    /**
     * Захист від перевизначення фіналізатора підтипами.
     */
    @Override
    @SuppressWarnings("removal")
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

    if (f.length != FIELD_COUNT
            && f.length != EXTENDED_FIELD_COUNT) {

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

        String dimensions = "UNKNOWN";
        ParcelStatus status = ParcelStatus.UNKNOWN;

        if (f.length == EXTENDED_FIELD_COUNT) {
            dimensions = f[5];

            try {
                status = ParcelStatus.valueOf(f[6]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "некоректний статус посилки", e);
            }
        }

        if (Boolean.parseBoolean(f[4])) {
            return new ExpressParcel(
                    f[0], weight, f[2], days,
                    dimensions, status);
        }

        return new RegularParcel(
                f[0], weight, f[2], days,
                dimensions, status);

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
     * Повертає габарити посилки.
     */
    public String getDimensions() {
        return dimensions;
    }

    /**
     * Повертає статус доставлення.
     */
    public ParcelStatus getStatus() {
        return status;
    }

    /**
     * Повертає вид доставлення.
     */
    public abstract DeliveryKind getDeliveryKind();

    /**
     * Розраховує термін доставлення.
     */
    public abstract int calculateDeliveryTime();

    /**
     * Розраховує вартість доставлення.
     */
    public abstract double calculateDeliveryCost();

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
                && city.equals(other.city)
                && dimensions.equals(other.dimensions)
                && status == other.status;
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
                express,
                dimensions,
                status
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
