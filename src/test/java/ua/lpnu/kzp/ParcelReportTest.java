package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ParcelReportTest {

    /* Коректний рядок: усі чотири показники обчислюються. */
    @Test
    void validRecordIsCounted() {
        String r = ParcelReport.buildReport(List.of("P001;2.5;Львів;2;true"));
        assertTrue(r.contains("Коректних записів: 1"));
        assertTrue(r.contains("Середня вага: 2.50 кг"));
        assertTrue(r.contains("Найбільший термін: 2 дн."));
        assertTrue(r.contains("Експрес-доставлень: 1"));
    }

    /* Середнє для кількох записів: (2.5 + 10.0 + 0.8) / 3 = 4.43. */
    @Test
    void averageOfSeveralRecords() {
        String r = ParcelReport.buildReport(List.of(
                "P001;2.5;Львів;2;true",
                "P002;10.0;Київ;4;false",
                "P003;0.8;Одеса;3;true"));
        assertTrue(r.contains("Середня вага: 4.43 кг"));
        assertTrue(r.contains("Найбільший термін: 4 дн."));
        assertTrue(r.contains("Експрес-доставлень: 2"));
    }

    /* Порожній рядок повідомляється з номером. */
    @Test
    void blankLineIsReported() {
        String r = ParcelReport.buildReport(List.of("P001;2.5;Львів;2;true", ""));
        assertTrue(r.contains("Рядок 2: порожній рядок"));
        assertTrue(r.contains("Коректних записів: 1"));
    }

    /* Неправильна кількість полів. */
    @Test
    void wrongFieldCountIsReported() {
        String r = ParcelReport.buildReport(List.of("P007;1.5;Львів"));
        assertTrue(r.contains("Рядок 1: очікується 5 полів, отримано 3"));
    }

    /* Нечислове поле не ламає програму. */
    @Test
    void nonNumericFieldIsReported() {
        String r = ParcelReport.buildReport(List.of("P004;abc;Харків;5;false"));
        assertTrue(r.contains("Рядок 1: числове поле має помилковий формат"));
        assertTrue(r.contains("Коректних записів: 0"));
    }

    /* Від'ємне значення. */
    @Test
    void negativeValueIsReported() {
        String r = ParcelReport.buildReport(List.of("P005;-3.0;Дніпро;2;true"));
        assertTrue(r.contains("Рядок 1: від'ємне числове значення"));
    }

    /* Порожній id або місто. */
    @Test
    void emptyIdIsReported() {
        String r = ParcelReport.buildReport(List.of(";4.0;Львів;3;false"));
        assertTrue(r.contains("Рядок 1: порожній id або місто"));
    }

    /* Хибне булеве значення. */
    @Test
    void invalidBooleanIsReported() {
        String r = ParcelReport.buildReport(List.of("P008;3.2;Тернопіль;3;maybe"));
        assertTrue(r.contains("express має бути true або false"));
    }

    /* Файл без коректних записів: середнє 0.00, без ділення на нуль. */
    @Test
    void noValidRecords() {
        String r = ParcelReport.buildReport(List.of("abc"));
        assertTrue(r.contains("Коректних записів: 0"));
        assertTrue(r.contains("Середня вага: 0.00 кг"));
        assertTrue(r.contains("немає жодного коректного запису"));
    }

    /* Українські символи в місті не впливають на результат. */
    @Test
    void ukrainianCityIsAccepted() {
        String r = ParcelReport.buildReport(List.of("P009;1.0;Івано-Франківськ;1;false"));
        assertTrue(r.contains("Коректних записів: 1"));
    }
}
