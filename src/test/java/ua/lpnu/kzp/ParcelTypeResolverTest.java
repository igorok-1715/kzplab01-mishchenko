package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тестування pattern matching switch.
 */
class ParcelTypeResolverTest {

    /* Перевірка звичайного доставлення. */
    @Test
    void describesRegularParcel() {
        Parcel parcel = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertEquals(
                "Звичайне доставлення",
                ParcelTypeResolver.describe(parcel));
    }

    /* Перевірка експрес-доставлення. */
    @Test
    void describesExpressParcel() {
        Parcel parcel = new ExpressParcel(
                "P002", 2.5, "Київ", 3);

        assertEquals(
                "Експрес-доставлення",
                ParcelTypeResolver.describe(parcel));
    }

    /* Перевірка відсутнього об'єкта. */
    @Test
    void rejectsNullParcel() {
        assertThrows(
                NullPointerException.class,
                () -> ParcelTypeResolver.describe(null));
    }
}