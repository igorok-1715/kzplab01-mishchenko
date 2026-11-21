
package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Інженерні тести лабораторної №2.
 * Варіант 15 — Доставлення посилок.
 */
class ParcelEngineeringTest {

    // 1. Граничні значення: вага 0 і термін 0
    @Test
    void csvAcceptsZeroBoundaryValues() {
        Parcel parcel = Parcel.fromCsv(
                "P100;0;Львів;0;false");

        assertEquals(0.0, parcel.getWeightKg());
        assertEquals(0, parcel.getDeliveryDays());
        assertFalse(parcel.isExpress());
    }

    // 2. Від'ємний термін доставки
    @Test
    void csvRejectsNegativeDeliveryDays() {
        assertThrows(IllegalArgumentException.class,
                () -> Parcel.fromCsv(
                        "P101;1.5;Київ;-1;true"));
    }

    // 3. Порожнє місто
    @Test
    void csvRejectsEmptyCity() {
        assertThrows(IllegalArgumentException.class,
                () -> Parcel.fromCsv(
                        "P102;1.5;;2;false"));
    }

    // 4. Зайве поле у CSV
    @Test
    void csvRejectsExtraField() {
        assertThrows(IllegalArgumentException.class,
                () -> Parcel.fromCsv(
                        "P103;1.5;Львів;2;true;extra"));
    }

    // 5. Нескінченна від'ємна вага
    @Test
    void csvRejectsNegativeInfinity() {
        assertThrows(IllegalArgumentException.class,
                () -> Parcel.fromCsv(
                        "P104;-Infinity;Львів;2;true"));
    }

    // 6. Перевірка record WeightDelivery
    @Test
    void recordAcceptsZeroValuesAndUsesValueEquality() {
        WeightDelivery first = new WeightDelivery(0.0, 0);
        WeightDelivery second = new WeightDelivery(0.0, 0);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    // 7. Record не приймає нескінченну вагу
    @Test
    void recordRejectsNegativeInfinity() {
        assertThrows(IllegalArgumentException.class,
                () -> new WeightDelivery(
                        Double.NEGATIVE_INFINITY, 1));
    }

    // 8. Збереження показників лабораторної №1
    @Test
    void reportPreservesLabOneStatisticsWithInvalidRow() {
        String report = ParcelReport.buildReport(List.of(
                "P001;2.0;Львів;3;true",
                "P002;4.0;Київ;5;false",
                "P003;-1.0;Одеса;2;true"));

        assertAll(
                () -> assertTrue(report.contains(
                        "Коректних записів: 2")),
                () -> assertTrue(report.contains(
                        "Середня вага: 3.00 кг")),
                () -> assertTrue(report.contains(
                        "Найбільший термін: 5 дн.")),
                () -> assertTrue(report.contains(
                        "Експрес-доставлень: 1")),
                () -> assertTrue(report.contains(
                        "Помилок: 1")));
    }
}
