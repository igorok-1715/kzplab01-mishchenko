
package ua.lpnu.kzp;

/**
 * Експрес-доставлення посилки.
 */
public final class ExpressParcel extends Parcel {

    public ExpressParcel(String id, double weightKg,
                         String city, int deliveryDays) {

        super(id, weightKg, city, deliveryDays, true);
    }

    public ExpressParcel(String id, double weightKg,
                         String city, int deliveryDays,
                         String dimensions, ParcelStatus status) {

        super(id, weightKg, city, deliveryDays,
                true, dimensions, status);
    }

    @Override
    public DeliveryKind getDeliveryKind() {
        return DeliveryKind.EXPRESS;
    }

    @Override
    public int calculateDeliveryTime() {
        return Math.max(0, getDeliveryDays() - 1);
    }

    @Override
    public double calculateDeliveryCost() {
        return 100.0 + getWeightKg() * 20.0;
    }
}
