package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульні тести універсального CSV-парсера.
 */
class CsvParserTest {

    @Test
    void parsesSimpleRecord() {
        assertEquals(
                List.of("P001", "2.5", "Львів"),
                CsvParser.parseLine("P001,2.5,Львів")
        );
    }

    @Test
    void parsesQuotedComma() {
        assertEquals(
                List.of("P001", "Львів, Україна"),
                CsvParser.parseLine("P001,\"Львів, Україна\"")
        );
    }

    @Test
    void parsesEscapedQuotes() {
        assertEquals(
                List.of("P001", "Місто \"Львів\""),
                CsvParser.parseLine("P001,\"Місто \"\"Львів\"\"\"")
        );
    }

    @Test
    void parsesLfRecords() {
        assertEquals(
                List.of(
                        List.of("P001", "Львів"),
                        List.of("P002", "Київ")
                ),
                CsvParser.parse("P001,Львів\nP002,Київ")
        );
    }

    @Test
    void parsesCrLfRecords() {
        assertEquals(
                List.of(
                        List.of("P001", "Львів"),
                        List.of("P002", "Київ")
                ),
                CsvParser.parse("P001,Львів\r\nP002,Київ")
        );
    }

    @Test
    void parsesMultilineField() {
        assertEquals(
                List.of("P001", "Львів\nУкраїна"),
                CsvParser.parseLine("P001,\"Львів\nУкраїна\"")
        );
    }

    @Test
    void preservesCrLfInsideField() {
        assertEquals(
                List.of("P001", "Львів\r\nУкраїна"),
                CsvParser.parseLine("P001,\"Львів\r\nУкраїна\"")
        );
    }

    @Test
    void parsesEmptyFields() {
        assertEquals(
                List.of("P001", "", ""),
                CsvParser.parseLine("P001,,")
        );
    }

    @Test
    void parsesEmptyDocument() {
        assertTrue(CsvParser.parse("").isEmpty());
    }

    @Test
    void rejectsUnclosedQuotes() {
        assertThrows(
                DataStorageException.class,
                () -> CsvParser.parseLine("P001,\"Львів")
        );
    }

    @Test
    void rejectsUnexpectedQuotes() {
        assertThrows(
                DataStorageException.class,
                () -> CsvParser.parseLine("P001,Ль\"вів")
        );
    }

    @Test
    void rejectsCharactersAfterClosingQuote() {
        assertThrows(
                DataStorageException.class,
                () -> CsvParser.parseLine("P001,\"Львів\"abc")
        );
    }

    @Test
    void rejectsMultipleRecordsInParseLine() {
        assertThrows(
                DataStorageException.class,
                () -> CsvParser.parseLine("P001\nP002")
        );
    }

    @Test
    void rejectsNullInput() {
        assertThrows(
                DataStorageException.class,
                () -> CsvParser.parse(null)
        );
    }
}