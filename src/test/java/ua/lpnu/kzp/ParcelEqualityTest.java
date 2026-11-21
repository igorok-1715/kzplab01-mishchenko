package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Перевірка логічної рівності посилок
 * та узгодженості equals() і hashCode().
 */
class ParcelEqualityTest {

    @Test
    void sameObjectIsEqual() {
        Parcel parcel = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertEquals(parcel, parcel);
    }

    @Test
    void equalRegularParcels() {
        Parcel first = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel second = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void equalExpressParcels() {
        Parcel first = new ExpressParcel(
                "P002", 5.0, "Київ", 2);

        Parcel second = new ExpressParcel(
                "P002", 5.0, "Київ", 2);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentIdsAreNotEqual() {
        Parcel first = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel second = new RegularParcel(
                "P002", 2.5, "Львів", 3);

        assertNotEquals(first, second);
    }

    @Test
    void differentWeightsAreNotEqual() {
        Parcel first = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel second = new RegularParcel(
                "P001", 5.0, "Львів", 3);

        assertNotEquals(first, second);
    }

    @Test
    void differentCitiesAreNotEqual() {
        Parcel first = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel second = new RegularParcel(
                "P001", 2.5, "Київ", 3);

        assertNotEquals(first, second);
    }

    @Test
    void differentDeliveryDaysAreNotEqual() {
        Parcel first = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel second = new RegularParcel(
                "P001", 2.5, "Львів", 5);

        assertNotEquals(first, second);
    }

    @Test
    void differentParcelTypesAreNotEqual() {
        Parcel regular = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel express = new ExpressParcel(
                "P001", 2.5, "Львів", 3);

        assertNotEquals(regular, express);
        assertNotEquals(express, regular);
    }

    @Test
    void parcelIsNotEqualToNull() {
        Parcel parcel = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertFalse(parcel.equals(null));
    }

    @Test
    void parcelIsNotEqualToString() {
        Parcel parcel = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertFalse(parcel.equals("P001"));
    }

    @Test
    void hashSetDoesNotStoreDuplicates() {
        Set<Parcel> parcels = new HashSet<>();

        parcels.add(new RegularParcel(
                "P001", 2.5, "Львів", 3));

        parcels.add(new RegularParcel(
                "P001", 2.5, "Львів", 3));

        assertEquals(1, parcels.size());
    }

    @Test
    void hashSetStoresDifferentParcelTypes() {
        Set<Parcel> parcels = new HashSet<>();

        parcels.add(new RegularParcel(
                "P001", 2.5, "Львів", 3));

        parcels.add(new ExpressParcel(
                "P001", 2.5, "Львів", 3));

        assertEquals(2, parcels.size());
    }

    @Test
    void equalityIsTransitive() {
        Parcel first = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel second = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        Parcel third = new RegularParcel(
                "P001", 2.5, "Львів", 3);

        assertEquals(first, second);
        assertEquals(second, third);
        assertEquals(first, third);
    }

    @Test
    void equalParcelsWithZeroValues() {
        Parcel first = new RegularParcel(
                "P001", 0.0, "Львів", 0);

        Parcel second = new RegularParcel(
                "P001", 0.0, "Львів", 0);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}