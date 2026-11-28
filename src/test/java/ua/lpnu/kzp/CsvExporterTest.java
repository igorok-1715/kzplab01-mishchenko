package ua.lpnu.kzp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульні тести CSV-експортера.
 */
class CsvExporterTest {

    private CsvExporter<ParcelRecord> exporter;
    private ParcelRecord parcel;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        exporter = new CsvExporter<>(ParcelRecord.class);

        parcel = new ParcelRecord(
                "P001",
                2.5,
                "20x15x10",
                "Львів",
                ParcelStatus.IN_TRANSIT
        );
    }

    @Test
    void headerContainsCorrectColumns() {
        assertEquals(
                "Номер,Вага,Габарити,Місто,Статус",
                exporter.header()
        );
    }

    @Test
    void rowContainsCorrectValues() {
        assertEquals(
                "P001,2.5,20x15x10,Львів,IN_TRANSIT",
                exporter.row(parcel)
        );
    }

    @Test
    void exportContainsHeaderAndRow() {
        String result = exporter.export(List.of(parcel));

        assertEquals(
                "Номер,Вага,Габарити,Місто,Статус\n"
                + "P001,2.5,20x15x10,Львів,IN_TRANSIT",
                result
        );
    }

    @Test
    void exportMultipleRecords() {
        ParcelRecord second = new ParcelRecord(
                "P002",
                10.0,
                "50x40x30",
                "Київ",
                ParcelStatus.DELIVERED
        );

        String result = exporter.export(
                List.of(parcel, second));

        assertEquals(3, result.lines().count());
        assertTrue(result.contains("P001"));
        assertTrue(result.contains("P002"));
    }

    @Test
    void exportEmptyList() {
        assertEquals(
                "Номер,Вага,Габарити,Місто,Статус\n",
                exporter.export(List.of())
        );
    }

    @Test
    void exportToFileUsesUtf8() throws IOException {
        Path file = tempDir.resolve("parcels.csv");

        exporter.exportToFile(List.of(parcel), file);

        assertTrue(Files.exists(file));

        String content = Files.readString(
                file, StandardCharsets.UTF_8);

        assertEquals(exporter.export(List.of(parcel)),
                content);

        assertTrue(content.contains("Львів"));
    }
}