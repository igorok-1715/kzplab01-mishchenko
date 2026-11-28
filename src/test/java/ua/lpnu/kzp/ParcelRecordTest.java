package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести імпорту CSV та round-trip для посилок.
 */
class ParcelRecordTest {

    private final CsvExporter<ParcelRecord> exporter =
            new CsvExporter<>(ParcelRecord.class);

    @Test
    void fromCsvCreatesCorrectRecord() {

        ParcelRecord parcel = ParcelRecord.fromCsv(
                "P001,2.5,20x15x10,Львів,IN_TRANSIT");

        assertEquals("P001", parcel.trackingNumber());
        assertEquals(2.5, parcel.weightKg());
        assertEquals("20x15x10", parcel.dimensions());
        assertEquals("Львів", parcel.city());
        assertEquals(ParcelStatus.IN_TRANSIT, parcel.status());
    }

    @Test
    void fromCsvFieldsCreatesCorrectRecord() {

        ParcelRecord parcel = ParcelRecord.fromCsvFields(
                List.of(
                        "P002",
                        "10.0",
                        "50x40x30",
                        "Київ",
                        "DELIVERED"
                )
        );

        assertEquals("P002", parcel.trackingNumber());
        assertEquals(10.0, parcel.weightKg());
        assertEquals("50x40x30", parcel.dimensions());
        assertEquals("Київ", parcel.city());
        assertEquals(ParcelStatus.DELIVERED, parcel.status());
    }

    @Test
    void roundTripPreservesSimpleRecord() {

        ParcelRecord original = new ParcelRecord(
                "P001",
                2.5,
                "20x15x10",
                "Львів",
                ParcelStatus.IN_TRANSIT
        );

        String csv = exporter.row(original);

        ParcelRecord restored = ParcelRecord.fromCsv(csv);

        assertEquals(original, restored);
    }

    @Test
    void roundTripPreservesSpecialCharacters() {

        ParcelRecord original = new ParcelRecord(
                "P002",
                10.0,
                "50x40x30",
                "Львів, відділення \"№1\"\nУкраїна",
                ParcelStatus.DELIVERED
        );

        String csv = exporter.row(original);

        ParcelRecord restored = ParcelRecord.fromCsv(csv);

        assertEquals(original, restored);
    }

    @Test
    void roundTripPreservesCrLf() {

        ParcelRecord original = new ParcelRecord(
                "P003",
                0.8,
                "15x10x5",
                "Одеса\r\nУкраїна",
                ParcelStatus.IN_TRANSIT
        );

        assertEquals(
                original,
                ParcelRecord.fromCsv(exporter.row(original))
        );
    }

    @Test
    void roundTripPreservesMultipleRecords() {

        List<ParcelRecord> original = List.of(
                new ParcelRecord(
                        "P001", 2.5, "20x15x10",
                        "Львів", ParcelStatus.IN_TRANSIT
                ),
                new ParcelRecord(
                        "P002", 10.0, "50x40x30",
                        "Київ", ParcelStatus.DELIVERED
                ),
                new ParcelRecord(
                        "P003", 0.8, "15x10x5",
                        "Одеса", ParcelStatus.IN_TRANSIT
                )
        );

        String csv = exporter.export(original);

        List<ParcelRecord> restored = CsvParser.parse(csv)
                .stream()
                .skip(1)
                .map(ParcelRecord::fromCsvFields)
                .toList();

        assertEquals(original, restored);
    }

    @Test
    void rejectsIncorrectFieldCount() {

        assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv("P001,2.5")
        );
    }

    @Test
    void rejectsInvalidWeightAndPreservesCause() {

        DataStorageException exception = assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv(
                        "P001,abc,20x15x10,Львів,IN_TRANSIT")
        );

        assertInstanceOf(
                NumberFormatException.class,
                exception.getCause()
        );
    }

    @Test
    void rejectsNegativeWeight() {

        assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv(
                        "P001,-2.5,20x15x10,Львів,IN_TRANSIT")
        );
    }

    @Test
    void rejectsZeroWeight() {

        assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv(
                        "P001,0,20x15x10,Львів,IN_TRANSIT")
        );
    }

    @Test
    void rejectsNonFiniteWeight() {

        assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv(
                        "P001,NaN,20x15x10,Львів,IN_TRANSIT")
        );
    }

    @Test
    void rejectsInvalidStatusAndPreservesCause() {

        DataStorageException exception = assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv(
                        "P001,2.5,20x15x10,Львів,CANCELLED")
        );

        assertInstanceOf(
                IllegalArgumentException.class,
                exception.getCause()
        );
    }

    @Test
    void rejectsEmptyTrackingNumber() {

        assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv(
                        ",2.5,20x15x10,Львів,IN_TRANSIT")
        );
    }

    @Test
    void rejectsEmptyCity() {

        assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsv(
                        "P001,2.5,20x15x10,,IN_TRANSIT")
        );
    }

    @Test
    void rejectsNullFields() {

        assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsvFields(null)
        );
    }

    @Test
    void rejectsNullFieldValue() {

        DataStorageException exception = assertThrows(
                DataStorageException.class,
                () -> ParcelRecord.fromCsvFields(
                        Arrays.asList(
                                "P001",
                                "2.5",
                                "20x15x10",
                                null,
                                "IN_TRANSIT"
                        )
                )
        );

        assertInstanceOf(
                IllegalArgumentException.class,
                exception.getCause()
        );
    }
}