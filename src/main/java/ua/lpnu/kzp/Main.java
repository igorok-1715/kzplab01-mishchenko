package ua.lpnu.kzp;

import java.io.IOException;
<<<<<<< HEAD
import java.io.PrintStream;
=======
>>>>>>> ef26bf8c572efa256afd757b6ae51510f8a3b52f
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
<<<<<<< HEAD
        run(args, System.out);
    }

    /**
     * Виконує програму з виводом у заданий потік (щоб логіку можна було тестувати).
     *
     * @param args аргументи командного рядка
     * @param out потік для повідомлень і звіту
     */
    static void run(String[] args, PrintStream out) {
=======
>>>>>>> ef26bf8c572efa256afd757b6ae51510f8a3b52f
        Path input = Path.of("data", "input.csv");
        Path output = Path.of("out", "report.txt");

        for (int i = 0; i < args.length; i++) {
            if ("--help".equals(args[i])) {
<<<<<<< HEAD
                out.printf("Використання: java -jar lab01.jar [--help] [--version] "
                        + "[--input <файл>] [--output <файл>]%n");
                return;
            } else if ("--version".equals(args[i])) {
                out.printf("lab01 %s%n", version());
                return;
            } else if ("--input".equals(args[i]) || "--output".equals(args[i])) {
                if (i + 1 >= args.length) {
                    out.printf("Для %s потрібне значення%n", args[i]);
                    return;
                }
                Path value = Path.of(args[i + 1]);
                if ("--input".equals(args[i])) {
                    input = value;
                } else {
                    output = value;
                }
                i++;
            } else {
                out.printf("Невідомий параметр: %s%n", args[i]);
                return;
=======
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
>>>>>>> ef26bf8c572efa256afd757b6ae51510f8a3b52f
            }
        }

        try {
            List<String> lines = FileReport.readLines(input);
            String report = ParcelReport.buildReport(lines);
<<<<<<< HEAD
            out.print(report);
            FileReport.writeReport(output, report);
        } catch (IOException e) {
            out.printf("Помилка файлу: %s%n", e.getMessage());
=======
            System.out.print(report);
            FileReport.writeReport(output, report);
        } catch (IOException e) {
            System.out.printf("Помилка файлу: %s%n", e.getMessage());
>>>>>>> ef26bf8c572efa256afd757b6ae51510f8a3b52f
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
