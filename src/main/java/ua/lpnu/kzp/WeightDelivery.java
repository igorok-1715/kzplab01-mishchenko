package ua.lpnu.kzp;

/**
 * Допоміжне незмінне значення: вага посилки та термін її доставлення.
 *
 * @param weightKg вага в кілограмах (скінченне число, не менше 0)
 * @param deliveryDays термін доставлення в днях (не менше 0)
 */
public record WeightDelivery(double weightKg, int deliveryDays) {

    /**
     * Перевіряє компоненти record під час створення.
     *
     * @throws IllegalArgumentException якщо вага не скінченна чи від'ємна або термін від'ємний
     */
    public WeightDelivery {
        if (!Double.isFinite(weightKg)) {
            throw new IllegalArgumentException("числове поле має помилковий формат");
        }
        if (weightKg < 0 || deliveryDays < 0) {
            throw new IllegalArgumentException("від'ємне числове значення");
        }
    }
}