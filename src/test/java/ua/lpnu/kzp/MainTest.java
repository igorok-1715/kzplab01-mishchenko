package ua.lpnu.kzp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainTest {

    private static String run(String... args) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Main.run(args, out);
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Test
    void helpPrintsUsage() {
        assertTrue(run("--help").contains("Використання"));
    }

    @Test
    void versionPrintsName() {
        assertTrue(run("--version").startsWith("lab01 "));
    }

    @Test
    void inputWithoutValueIsReported() {
        assertTrue(run("--input").contains("Для --input потрібне значення"));
    }

    @Test
    void outputWithoutValueIsReported() {
        assertTrue(run("--output").contains("Для --output потрібне значення"));
    }

    @Test
    void unknownOptionIsReported() {
        assertTrue(run("--inpt").contains("Невідомий параметр: --inpt"));
    }

    @Test
    void missingInputFileIsReported(@TempDir Path dir) {
        String text = run("--input", dir.resolve("nope.csv").toString());
        assertTrue(text.contains("Помилка файлу"));
    }

    @Test
    void reportIsPrintedAndWritten(@TempDir Path dir) throws IOException {
        Path in = dir.resolve("in.csv");
        Path out = dir.resolve("sub").resolve("report.txt");
        Files.writeString(in, "P001;2.0;Львів;3;true\nP002;4.0;Київ;5;false\n",
                StandardCharsets.UTF_8);

        String text = run("--input", in.toString(), "--output", out.toString());

        assertTrue(text.contains("Коректних записів: 2"));
        assertTrue(text.contains("Середня вага: 3.00 кг"));
        String written = Files.readString(out, StandardCharsets.UTF_8);
        assertTrue(written.contains("Найбільший термін: 5 дн."));
    }
}
