
package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParcelPolymorphismTest {

    @Test
    void regularParcelHasCorrectType() {
        Parcel parcel = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertEquals(DeliveryKind.REGULAR,
                parcel.getDeliveryKind());
        assertFalse(parcel.isExpress());
    }

    @Test
    void expressParcelHasCorrectType() {
        Parcel parcel = new ExpressParcel(
                "P002", 2.5, "Київ", 3);

        assertEquals(DeliveryKind.EXPRESS,
                parcel.getDeliveryKind());
        assertTrue(parcel.isExpress());
    }

    @Test
    void regularParcelCalculatesDeliveryTime() {
        Parcel parcel = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertEquals(3, parcel.calculateDeliveryTime());
    }

    @Test
    void expressParcelCalculatesDeliveryTime() {
        Parcel parcel = new ExpressParcel(
                "P002", 2.5, "Київ", 3);

        assertEquals(2, parcel.calculateDeliveryTime());
    }

    @Test
    void regularParcelCalculatesCost() {
        Parcel parcel = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertEquals(75.0,
                parcel.calculateDeliveryCost(), 0.001);
    }

    @Test
    void expressParcelCalculatesCost() {
        Parcel parcel = new ExpressParcel(
                "P002", 2.5, "Київ", 3);

        assertEquals(150.0,
                parcel.calculateDeliveryCost(), 0.001);
    }

    @Test
    void polymorphismWorksThroughBaseType() {
        Parcel[] parcels = {
                new RegularParcel("P001", 2.5, "Львів", 3),
                new ExpressParcel("P002", 2.5, "Київ", 3)
        };

        assertEquals(75.0,
                parcels[0].calculateDeliveryCost(), 0.001);
        assertEquals(150.0,
                parcels[1].calculateDeliveryCost(), 0.001);
    }

    @Test
    void fromCsvCreatesRegularParcel() {
        Parcel parcel = Parcel.fromCsv(
                "P001;2.5;Львів;3;false");

        assertInstanceOf(RegularParcel.class, parcel);
    }

    @Test
    void fromCsvCreatesExpressParcel() {
        Parcel parcel = Parcel.fromCsv(
                "P002;2.5;Київ;3;true");

        assertInstanceOf(ExpressParcel.class, parcel);
    }

    @Test
    void expressTimeNeverBecomesNegative() {
        Parcel parcel = new ExpressParcel(
                "P003", 1.0, "Одеса", 0);

        assertEquals(0, parcel.calculateDeliveryTime());
    }

    @Test
    void invalidWeightIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new RegularParcel(
                        "P004", -1.0, "Львів", 2));
    }

    @Test
    void builderCreatesCorrectSubtype() {
        Parcel parcel = Parcel.builder()
                .id("P005")
                .weightKg(3.0)
                .city("Львів")
                .deliveryDays(2)
                .express(true)
                .build();

        assertInstanceOf(ExpressParcel.class, parcel);
    }
}
