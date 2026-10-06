package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ParcelReportTest {

    @Test
    void validLine() {
        String r = ParcelReport.buildReport(List.of("P001;2.5;Львів;2;true"));
        assertTrue(r.contains("Коректних записів: 1"));
        assertTrue(r.contains("Середня вага: 2.50 кг"));
        assertTrue(r.contains("Найбільший термін: 2 дн."));
        assertTrue(r.contains("Експрес-доставлень: 1"));
        assertTrue(r.contains("Помилок: 0"));
    }

    @Test
    void averageOfSeveralRecords() {
        String r = ParcelReport.buildReport(List.of(
                "P001;2.5;Львів;2;true",
                "P002;10.0;Київ;4;false",
                "P003;0.8;Одеса;3;true"));
        assertTrue(r.contains("Коректних записів: 3"));
        assertTrue(r.contains("Середня вага: 4.43 кг"));
        assertTrue(r.contains("Найбільший термін: 4 дн."));
        assertTrue(r.contains("Експрес-доставлень: 2"));
    }

    @Test
    void blankLine() {
        String r = ParcelReport.buildReport(List.of("   "));
        assertTrue(r.contains("Рядок 1: порожній рядок"));
    }

    @Test
    void wrongFieldCount() {
        String r = ParcelReport.buildReport(List.of("P007;1.5;Львів"));
        assertTrue(r.contains("Рядок 1: очікується 5 полів, отримано 3"));
    }

    @Test
    void nonNumericField() {
        String r = ParcelReport.buildReport(List.of("P004;abc;Харків;5;false"));
        assertTrue(r.contains("числове поле має помилковий формат"));
    }

    @Test
    void negativeValue() {
        String r = ParcelReport.buildReport(List.of("P005;-3.0;Дніпро;2;true"));
        assertTrue(r.contains("від'ємне числове значення"));
    }

    @Test
    void emptyId() {
        String r = ParcelReport.buildReport(List.of(";4.0;Львів;3;false"));
        assertTrue(r.contains("порожній id або місто"));
    }

    @Test
    void invalidExpress() {
        String r = ParcelReport.buildReport(List.of("P008;3.2;Тернопіль;3;maybe"));
        assertTrue(r.contains("express має бути true або false"));
    }

    @Test
    void noValidRecordsDoesNotDivideByZero() {
        String r = ParcelReport.buildReport(List.of("bad"));
        assertTrue(r.contains("Коректних записів: 0"));
        assertTrue(r.contains("Середня вага: 0.00 кг"));
        assertTrue(r.contains("Увага: немає жодного коректного запису"));
    }

    @Test
    void ukrainianCharactersSurvive() {
        String r = ParcelReport.buildReport(List.of("P009;1.0;Івано-Франківськ;1;false"));
        assertTrue(r.contains("Коректних записів: 1"));
    }
    
       @Test
       void nanWeightIsRejected() {
           String r = ParcelReport.buildReport(List.of("P010;NaN;Львів;2;false"));
           assertTrue(r.contains("Коректних записів: 0"));
           assertTrue(r.contains("числове поле має помилковий формат"));
       }

       @Test
       void infinityWeightIsRejected() {
           String r = ParcelReport.buildReport(List.of("P011;Infinity;Львів;2;false"));
           assertTrue(r.contains("Коректних записів: 0"));
       }
}
