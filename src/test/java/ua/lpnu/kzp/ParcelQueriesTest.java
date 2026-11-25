
package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестування базових запитів Stream API.
 * Варіант 15 — Доставлення посилок.
 */
class ParcelQueriesTest {

    private List<Parcel> createParcels() {
        return List.of(
                new ExpressParcel(
                        "P001", 2.5, "Львів", 2,
                        "20x15x10", ParcelStatus.IN_TRANSIT),

                new RegularParcel(
                        "P002", 10.0, "Київ", 4,
                        "50x40x30", ParcelStatus.DELIVERED),

                new ExpressParcel(
                        "P003", 0.8, "Одеса", 3,
                        "15x10x5", ParcelStatus.IN_TRANSIT)
        );
    }

    @Test
    void filterInTransitReturnsCorrectParcels() {
        List<Parcel> result =
                ParcelQueries.filterInTransit(createParcels());

        assertEquals(2, result.size());
        assertEquals("P001", result.get(0).getId());
        assertEquals("P003", result.get(1).getId());
    }

    @Test
    void mapCitiesReturnsCorrectCities() {
        List<String> result =
                ParcelQueries.mapCities(createParcels());

        assertEquals(
                List.of("Львів", "Київ", "Одеса"),
                result
        );
    }

    @Test
    void countByCityReturnsCorrectCounts() {
        Map<String, Long> result =
                ParcelQueries.countByCity(createParcels());

        assertEquals(
                Map.of(
                        "Львів", 1L,
                        "Київ", 1L,
                        "Одеса", 1L
                ),
                result
        );
    }

    @Test
    void filterInTransitHandlesEmptyInput() {
        assertTrue(
                ParcelQueries.filterInTransit(List.of()).isEmpty()
        );
    }

    @Test
    void mapCitiesHandlesEmptyInput() {
        assertTrue(
                ParcelQueries.mapCities(List.of()).isEmpty()
        );
    }

    @Test
    void countByCityHandlesEmptyInput() {
        assertTrue(
                ParcelQueries.countByCity(List.of()).isEmpty()
        );
    }
}
