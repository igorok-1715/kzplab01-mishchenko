package ua.lpnu.kzp;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** Консольна програма обробки доставлень посилок (варіант 15). */
public final class Main {

    /* Забороняє створення екземплярів службового класу. */
    private Main() {
    }

    /**
     * Точка входу до програми.
     *
     * @param args аргументи командного рядка: --help, --version, --input, --output
     */
    public static void main(String[] args) {
        Path input = Path.of("data", "input.csv");
        Path output = Path.of("out", "report.txt");

        for (int i = 0; i < args.length; i++) {
            if ("--help".equals(args[i])) {
                System.out.printf("Використання: java -jar lab01.jar [--help] [--version] "
                        + "[--input <файл>] [--output <файл>]%n");
                return;
            } else if ("--version".equals(args[i])) {
                System.out.printf("lab01 %s%n", version());
                return;
            } else if ("--input".equals(args[i]) && i + 1 < args.length) {
                i++;
                input = Path.of(args[i]);
            } else if ("--output".equals(args[i]) && i + 1 < args.length) {
                i++;
                output = Path.of(args[i]);
            }
        }

        try {
            List<String> lines = FileReport.readLines(input);
            String report = ParcelReport.buildReport(lines);
            System.out.print(report);
            FileReport.writeReport(output, report);
        } catch (IOException e) {
            System.out.printf("Помилка файлу: %s%n", e.getMessage());
        }
    }

    /**
     * Повертає версію програми з маніфесту JAR (її підставляє Maven з pom.xml).
     *
     * @return версія або "dev", якщо запуск не з JAR
     */
    static String version() {
        String v = Main.class.getPackage().getImplementationVersion();
        return v == null ? "dev" : v;
    }
}
