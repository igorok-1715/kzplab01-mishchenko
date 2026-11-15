
package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParcelBuilderTest {

    @Test
    void validBuilder() {
        Parcel p = new Parcel.Builder()
                .id("P001")
                .weightKg(2.5)
                .city("Lviv")
                .deliveryDays(3)
                .express(true)
                .build();

        assertEquals("P001", p.getId());
        assertEquals(2.5, p.getWeightKg());
        assertEquals("Lviv", p.getCity());
        assertEquals(3, p.getDeliveryDays());
        assertTrue(p.isExpress());
    }

    @Test
    void negativeWeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel.Builder()
                        .id("P002")
                        .weightKg(-5)
                        .city("Kyiv")
                        .deliveryDays(2)
                        .build());
    }

    @Test
    void emptyCity() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel.Builder()
                        .id("P003")
                        .weightKg(3)
                        .city("")
                        .deliveryDays(2)
                        .build());
    }

    @Test
    void missingWeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel.Builder()
                        .id("P004")
                        .city("Lviv")
                        .deliveryDays(2)
                        .build());
    }

    @Test
    void missingDeliveryDays() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel.Builder()
                        .id("P005")
                        .weightKg(2)
                        .city("Lviv")
                        .build());
    }

    @Test
    void negativeDeliveryDays() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel.Builder()
                        .id("P006")
                        .weightKg(2)
                        .city("Lviv")
                        .deliveryDays(-1)
                        .build());
    }

    @Test
    void nullId() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel.Builder()
                        .id(null)
                        .weightKg(2)
                        .city("Lviv")
                        .deliveryDays(2)
                        .build());
    }

    @Test
    void infinityWeight() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel.Builder()
                        .id("P007")
                        .weightKg(Double.POSITIVE_INFINITY)
                        .city("Lviv")
                        .deliveryDays(2)
                        .build());
    }
}
