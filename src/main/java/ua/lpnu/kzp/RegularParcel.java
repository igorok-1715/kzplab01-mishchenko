package ua.lpnu.kzp;

/**
 * Звичайне доставлення посилки.
 */
public final class RegularParcel extends Parcel {

    public RegularParcel(String id, double weightKg,
                         String city, int deliveryDays) {
        super(id, weightKg, city, deliveryDays, false);
    }

    @Override
    public DeliveryKind getDeliveryKind() {
        return DeliveryKind.REGULAR;
    }

    @Override
    public int calculateDeliveryTime() {
        return getDeliveryDays();
    }

    @Override
    public double calculateDeliveryCost() {
        return 50.0 + getWeightKg() * 10.0;
    }
}