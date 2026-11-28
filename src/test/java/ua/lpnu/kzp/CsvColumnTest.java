package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульні тести анотації CsvColumn.
 */
class CsvColumnTest {

    @Test
    void annotationAvailableAtRuntime() {
        Retention retention =
                CsvColumn.class.getAnnotation(Retention.class);

        assertNotNull(retention);
        assertEquals(RetentionPolicy.RUNTIME, retention.value());
    }

    @Test
    void trackingNumberHasCorrectAnnotation()
            throws NoSuchFieldException {

        Field field =
                ParcelRecord.class.getDeclaredField("trackingNumber");

        CsvColumn annotation =
                field.getAnnotation(CsvColumn.class);

        assertNotNull(annotation);
        assertEquals("Номер", annotation.name());
        assertEquals(1, annotation.order());
    }

    @Test
    void allParcelFieldsAreAnnotated() {
        long count = Arrays.stream(
                        ParcelRecord.class.getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(CsvColumn.class))
                .count();

        assertEquals(5, count);
    }

    @Test
    void statusHasCorrectAnnotation()
            throws NoSuchFieldException {

        Field field =
                ParcelRecord.class.getDeclaredField("status");

        CsvColumn annotation =
                field.getAnnotation(CsvColumn.class);

        assertNotNull(annotation);
        assertEquals("Статус", annotation.name());
        assertEquals(5, annotation.order());
    }
}