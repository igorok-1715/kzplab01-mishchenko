package ua.lpnu.kzp;

import java.util.ArrayList;
import java.util.List;

/**
 * Універсальний парсер CSV-документів.
 */
public final class CsvParser {

    private CsvParser() {
    }

    /**
     * Розбирає CSV-документ на записи та поля.
     */
    public static List<List<String>> parse(String csv) {

        if (csv == null) {
            throw new DataStorageException(
                    "CSV не може бути null");
        }

        List<List<String>> records = new ArrayList<>();
        List<String> fields = new ArrayList<>();

        StringBuilder field = new StringBuilder();

        boolean inQuotes = false;
        boolean closedQuote = false;

        for (int i = 0; i < csv.length(); i++) {

            char ch = csv.charAt(i);

            if (inQuotes) {

                if (ch == '"') {

                    if (i + 1 < csv.length()
                            && csv.charAt(i + 1) == '"') {

                        field.append('"');
                        i++;

                    } else {
                        inQuotes = false;
                        closedQuote = true;
                    }

                } else {
                    field.append(ch);
                }

            } else if (ch == ',') {

                fields.add(field.toString());
                field.setLength(0);
                closedQuote = false;

            } else if (ch == '\n' || ch == '\r') {

                fields.add(field.toString());
                records.add(List.copyOf(fields));

                fields.clear();
                field.setLength(0);
                closedQuote = false;

                if (ch == '\r'
                        && i + 1 < csv.length()
                        && csv.charAt(i + 1) == '\n') {
                    i++;
                }

            } else if (ch == '"') {

                if (field.length() != 0 || closedQuote) {
                    throw new DataStorageException(
                            "Некоректні лапки у CSV");
                }

                inQuotes = true;

            } else {

                if (closedQuote) {
                    throw new DataStorageException(
                            "Некоректний символ після лапок");
                }

                field.append(ch);
            }
        }

        if (inQuotes) {
            throw new DataStorageException(
                    "Незакриті лапки у CSV");
        }

        if (field.length() > 0
                || !fields.isEmpty()
                || closedQuote) {

            fields.add(field.toString());
            records.add(List.copyOf(fields));
        }

        return List.copyOf(records);
    }

    /**
     * Розбирає один логічний CSV-запис.
     */
    public static List<String> parseLine(String line) {

        List<List<String>> records = parse(line);

        if (records.size() != 1) {
            throw new DataStorageException(
                    "Очікується один CSV-запис");
        }

        return records.get(0);
    }
}