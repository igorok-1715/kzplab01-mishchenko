package ua.lpnu.kzp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестування базових та інженерних запитів Stream API
 * і дворівневого групування.
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

    // Рівень 1 — базові тести

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

    // Рівень 2 — інженерні тести

    /**
     * Перевірка статистичних показників ваги.
     */
    @Test
    void weightStatisticsReturnsCorrectValues() {
        DoubleSummaryStatistics stats =
                ParcelQueries.weightStatistics(createParcels());

        assertEquals(3, stats.getCount());
        assertEquals(13.3, stats.getSum(), 0.001);
        assertEquals(0.8, stats.getMin(), 0.001);
        assertEquals(10.0, stats.getMax(), 0.001);
        assertEquals(13.3 / 3, stats.getAverage(), 0.001);
    }

    /**
     * Перевірка статистики порожньої колекції.
     */
    @Test
    void weightStatisticsHandlesEmptyInput() {
        DoubleSummaryStatistics stats =
                ParcelQueries.weightStatistics(List.of());

        assertEquals(0, stats.getCount());
        assertEquals(0.0, stats.getSum(), 0.001);
        assertEquals(0.0, stats.getAverage(), 0.001);
        assertEquals(Double.POSITIVE_INFINITY, stats.getMin());
        assertEquals(Double.NEGATIVE_INFINITY, stats.getMax());
    }

    /**
     * Перевірка групування повторюваних записів.
     */
    @Test
    void countByCityHandlesDuplicates() {
        Parcel parcel = createParcels().get(0);

        List<Parcel> parcels = List.of(
                parcel,
                parcel,
                createParcels().get(1)
        );

        assertEquals(
                Map.of("Львів", 2L, "Київ", 1L),
                ParcelQueries.countByCity(parcels)
        );
    }

    /**
     * Перевірка сортування за спаданням ваги.
     */
    @Test
    void topByWeightReturnsCorrectOrder() {
        List<Parcel> result =
                ParcelQueries.topByWeight(createParcels(), 5);

        assertEquals(
                List.of("P002", "P001", "P003"),
                result.stream()
                        .map(Parcel::getId)
                        .toList()
        );
    }

    /**
     * Перевірка сортування за номером
     * при однаковій вазі посилок.
     */
    @Test
    void topByWeightSortsByIdWhenWeightsAreEqual() {
        List<Parcel> parcels = List.of(
                new RegularParcel(
                        "P003", 5.0, "Львів", 2,
                        "20x15x10", ParcelStatus.IN_TRANSIT),

                new RegularParcel(
                        "P001", 5.0, "Київ", 2,
                        "20x15x10", ParcelStatus.IN_TRANSIT),

                new RegularParcel(
                        "P002", 5.0, "Одеса", 2,
                        "20x15x10", ParcelStatus.IN_TRANSIT)
        );

        List<Parcel> result =
                ParcelQueries.topByWeight(parcels, 3);

        assertEquals(
                List.of("P001", "P002", "P003"),
                result.stream()
                        .map(Parcel::getId)
                        .toList()
        );
    }

    /**
     * Перевірка граничного значення N = 0.
     */
    @Test
    void topByWeightHandlesZeroLimit() {
        assertTrue(
                ParcelQueries.topByWeight(createParcels(), 0)
                        .isEmpty()
        );
    }

    /**
     * Перевірка граничного значення N = 1.
     */
    @Test
    void topByWeightHandlesSingleLimit() {
        List<Parcel> result =
                ParcelQueries.topByWeight(createParcels(), 1);

        assertEquals(1, result.size());
        assertEquals("P002", result.get(0).getId());
    }

    /**
     * Перевірка N, більшого за розмір колекції.
     */
    @Test
    void topByWeightHandlesLimitGreaterThanSize() {
        List<Parcel> result =
                ParcelQueries.topByWeight(createParcels(), 10);

        assertEquals(3, result.size());
    }

    /**
     * Перевірка від'ємного значення N.
     */
    @Test
    void topByWeightRejectsNegativeLimit() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ParcelQueries.topByWeight(createParcels(), -1)
        );
    }

    /**
     * Перевірка top-N для порожньої колекції.
     */
    @Test
    void topByWeightHandlesEmptyInput() {
        assertTrue(
                ParcelQueries.topByWeight(List.of(), 5)
                        .isEmpty()
        );
    }

    /**
     * Перевірка успішного пошуку посилки.
     */
    @Test
    void findByIdReturnsExistingParcel() {
        Optional<Parcel> result =
                ParcelQueries.findById(createParcels(), "P001");

        assertTrue(result.isPresent());
        assertEquals("P001", result.orElseThrow().getId());
        assertEquals("Львів", result.orElseThrow().getCity());
    }

    /**
     * Перевірка пошуку неіснуючої посилки.
     */
    @Test
    void findByIdReturnsEmptyForMissingParcel() {
        Optional<Parcel> result =
                ParcelQueries.findById(createParcels(), "P999");

        assertTrue(result.isEmpty());
    }

    /**
     * Перевірка пошуку в порожній колекції.
     */
    @Test
    void findByIdHandlesEmptyInput() {
        assertTrue(
                ParcelQueries.findById(List.of(), "P001")
                        .isEmpty()
        );
    }

    // Завдання із зірочкою — дворівневе групування

    /**
     * Перевірка групування за статусом та містом
     * з обчисленням сумарної ваги посилок.
     */
    @Test
    void groupWeightByStatusAndCityReturnsCorrectValues() {
        List<Parcel> parcels =
                new ArrayList<>(createParcels());

        parcels.add(new RegularParcel(
                "P004", 3.5, "Львів", 3,
                "30x20x10", ParcelStatus.IN_TRANSIT));

        parcels.add(new RegularParcel(
                "P005", 4.0, "Львів", 2,
                "25x20x15", ParcelStatus.DELIVERED));

        Map<ParcelStatus, Map<String, Double>> result =
                ParcelQueries.groupWeightByStatusAndCity(parcels);

        assertEquals(
                Map.of(
                        ParcelStatus.IN_TRANSIT,
                        Map.of(
                                "Львів", 6.0,
                                "Одеса", 0.8
                        ),

                        ParcelStatus.DELIVERED,
                        Map.of(
                                "Київ", 10.0,
                                "Львів", 4.0
                        )
                ),
                result
        );
    }

    /**
     * Перевірка дворівневого групування
     * для порожньої колекції.
     */
    @Test
    void groupWeightByStatusAndCityHandlesEmptyInput() {
        assertTrue(
                ParcelQueries.groupWeightByStatusAndCity(
                        List.of()).isEmpty()
        );
    }

    /**
     * Перевірка відсутності мутації вхідної колекції.
     */
    @Test
    void groupWeightByStatusAndCityDoesNotMutateInput() {
        List<Parcel> parcels =
                new ArrayList<>(createParcels());

        List<Parcel> original =
                new ArrayList<>(parcels);

        ParcelQueries.groupWeightByStatusAndCity(parcels);

        assertEquals(original.size(), parcels.size());
        assertEquals(original, parcels);
    }
}