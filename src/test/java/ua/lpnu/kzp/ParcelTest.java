package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import org.junit.jupiter.api.Test;

class ParcelTest {

    /* Коректні значення зберігаються й читаються через getter-и. */
    @Test
    void constructorStoresValidValues() {
        Parcel p = new Parcel("P001", 2.5, "Львів", 2, true);
        assertEquals("P001", p.getId());
        assertEquals(2.5, p.getWeightKg(), 0.0001);
        assertEquals("Львів", p.getCity());
        assertEquals(2, p.getDeliveryDays());
        assertTrue(p.isExpress());
    }

    /* Граничні значення: нульові вага й термін допустимі. */
    @Test
    void zeroWeightAndDaysAreAccepted() {
        Parcel p = new Parcel("P0", 0.0, "Київ", 0, false);
        assertEquals(0.0, p.getWeightKg(), 0.0001);
        assertEquals(0, p.getDeliveryDays());
        assertFalse(p.isExpress());
    }

    /* Порожній або відсутній id не допускається. */
    @Test
    void blankIdIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Parcel(null, 1.0, "Київ", 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Parcel("", 1.0, "Київ", 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Parcel("  ", 1.0, "Київ", 1, false));
    }

    /* Порожнє або відсутнє місто не допускається. */
    @Test
    void blankCityIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Parcel("P1", 1.0, null, 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Parcel("P1", 1.0, " ", 1, false));
    }

    /* Від'ємна вага або термін не допускаються. */
    @Test
    void negativeValuesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Parcel("P1", -0.1, "Київ", 1, false));
        assertThrows(IllegalArgumentException.class, () -> new Parcel("P1", 1.0, "Київ", -1, false));
    }

    /* NaN та Infinity у вазі не допускаються. */
    @Test
    void nonFiniteWeightIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel("P1", Double.NaN, "Київ", 1, false));
        assertThrows(IllegalArgumentException.class,
                () -> new Parcel("P1", Double.POSITIVE_INFINITY, "Київ", 1, false));
    }

    /* fromCsv створює об'єкт із коректного рядка, українські символи зберігаються. */
    @Test
    void fromCsvParsesValidLine() {
        Parcel p = Parcel.fromCsv("P002;10.0;Івано-Франківськ;4;false");
        assertEquals("P002", p.getId());
        assertEquals(10.0, p.getWeightKg(), 0.0001);
        assertEquals("Івано-Франківськ", p.getCity());
        assertEquals(4, p.getDeliveryDays());
        assertFalse(p.isExpress());
    }

    /* fromCsv відхиляє порожній рядок і null. */
    @Test
    void fromCsvRejectsBlankLine() {
        IllegalArgumentException e =
                assertThrows(IllegalArgumentException.class, () -> Parcel.fromCsv(" "));
        assertEquals("порожній рядок", e.getMessage());
        assertThrows(IllegalArgumentException.class, () -> Parcel.fromCsv(null));
    }

    /* fromCsv відхиляє хибну кількість полів. */
    @Test
    void fromCsvRejectsWrongFieldCount() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parcel.fromCsv("P007;1.5;Львів"));
        assertEquals("очікується 5 полів, отримано 3", e.getMessage());
    }

    /* fromCsv відхиляє хибне значення express. */
    @Test
    void fromCsvRejectsInvalidExpress() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parcel.fromCsv("P008;3.2;Тернопіль;3;maybe"));
        assertEquals("express має бути true або false", e.getMessage());
    }

    /* fromCsv відхиляє нечислові значення. */
    @Test
    void fromCsvRejectsNonNumericFields() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> Parcel.fromCsv("P004;abc;Харків;5;false"));
        assertEquals("числове поле має помилковий формат", e.getMessage());
        assertThrows(IllegalArgumentException.class, () -> Parcel.fromCsv("P004;1.0;Харків;x;false"));
    }

    /* fromCsv передає правила предметної області конструктору. */
    @Test
    void fromCsvAppliesConstructorRules() {
        assertThrows(IllegalArgumentException.class, () -> Parcel.fromCsv("P005;-3.0;Дніпро;2;true"));
        assertThrows(IllegalArgumentException.class, () -> Parcel.fromCsv(";4.0;Львів;3;false"));
        assertThrows(IllegalArgumentException.class, () -> Parcel.fromCsv("P9;NaN;Київ;1;true"));
    }

    /* toString не залежить від локалі: десяткова крапка. */
    @Test
    void toStringUsesLocaleRoot() {
        Locale saved = Locale.getDefault();
        try {
            Locale.setDefault(Locale.GERMANY);
            String s = new Parcel("P001", 2.5, "Львів", 2, true).toString();
            assertTrue(s.contains("weightKg=2.50"));
        } finally {
            Locale.setDefault(saved);
        }
    }

    /* record WeightDelivery зберігає значення й порівнюється за вмістом. */
    @Test
    void weightDeliveryHoldsValues() {
        WeightDelivery wd = new Parcel("P001", 2.5, "Львів", 2, true).toWeightDelivery();
        assertEquals(2.5, wd.weightKg(), 0.0001);
        assertEquals(2, wd.deliveryDays());
        assertEquals(new WeightDelivery(2.5, 2), wd);
    }

    /* record WeightDelivery відхиляє хибні значення. */
    @Test
    void weightDeliveryRejectsInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new WeightDelivery(-1.0, 1));
        assertThrows(IllegalArgumentException.class, () -> new WeightDelivery(1.0, -1));
        assertThrows(IllegalArgumentException.class, () -> new WeightDelivery(Double.NaN, 1));
    }
}