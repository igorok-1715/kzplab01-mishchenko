package ua.lpnu.kzp;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Універсальний CSV-експортер на основі Reflection API.
 */
public final class CsvExporter<T> {

    private final Class<T> type;

    public CsvExporter(Class<T> type) {
        this.type = type;
    }

    /**
     * Отримує поля з анотацією @CsvColumn
     * та сортує їх за порядком колонок.
     */
    private List<Field> getFields() {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(CsvColumn.class))
                .sorted(Comparator.comparingInt(field ->
                        field.getAnnotation(CsvColumn.class).order()))
                .toList();
    }

    /**
     * Формує заголовок CSV.
     */
    public String header() {
        return getFields().stream()
                .map(field ->
                        field.getAnnotation(CsvColumn.class).name())
                .map(this::escape)
                .collect(Collectors.joining(","));
    }

    /**
     * Формує CSV-рядок одного об'єкта.
     */
    public String row(T item) {
        return getFields().stream()
                .map(field -> getValue(field, item))
                .map(this::escape)
                .collect(Collectors.joining(","));
    }

    /**
     * Екранує спеціальні символи CSV.
     */
    private String escape(String value) {
        if (value.contains(",")
                || value.contains("\"")
                || value.contains("\n")
                || value.contains("\r")) {

            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }

    /**
     * Отримує значення поля через Reflection API.
     */
    private String getValue(Field field, T item) {
        try {
            field.setAccessible(true);

            Object value = field.get(item);

            return value == null ? "" : value.toString();

        } catch (IllegalAccessException e) {
            throw new DataStorageException(
                    "Помилка отримання значення поля: "
                            + field.getName(), e);
        }
    }

    /**
     * Формує повний CSV-документ.
     */
    public String export(List<T> items) {
        return header() + "\n"
                + items.stream()
                        .map(this::row)
                        .collect(Collectors.joining("\n"));
    }

    /**
     * Записує CSV-документ у файл
     * із явним кодуванням UTF-8.
     */
    public void exportToFile(List<T> items, Path path) {
        try {
            Path parent = path.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.writeString(
                    path,
                    export(items),
                    StandardCharsets.UTF_8
            );

        } catch (IOException e) {
            throw new DataStorageException(
                    "Помилка запису CSV-файла: " + path, e);
        }
    }
}