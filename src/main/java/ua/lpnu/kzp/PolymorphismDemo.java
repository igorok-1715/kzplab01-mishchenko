package ua.lpnu.kzp;

/**
 * Демонстрація поліморфізму для лабораторної №3.
 */
public final class PolymorphismDemo {

    private PolymorphismDemo() {
    }

    public static void main(String[] args) {

        Parcel regular = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel express = new ExpressParcel(
                "P002", 2.5, "Київ", 3);

        Parcel[] parcels = {regular, express};

        for (Parcel parcel : parcels) {
            System.out.println(
                    "Тип: " + parcel.getDeliveryKind());

            System.out.println(
                    "Термін: " + parcel.calculateDeliveryTime());

            System.out.println(
                    "Вартість: " + parcel.calculateDeliveryCost());

            System.out.println();
        }
    }
}
