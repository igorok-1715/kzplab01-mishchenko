package ua.lpnu.kzp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Модульні тести узагальненого сховища.
 */
class RepositoryTest {

    private Repository<ParcelRecord> repository;
    private ParcelRecord parcel;

    @BeforeEach
    void setUp() {
        repository = new Repository<>(
                ParcelRecord::trackingNumber);

        parcel = new ParcelRecord(
                "P001",
                2.5,
                "20x15x10",
                "Львів",
                ParcelStatus.IN_TRANSIT
        );
    }

    @Test
    void saveAndFindById() {
        repository.save(parcel);

        assertEquals(parcel,
                repository.findById("P001").orElseThrow());
    }

    @Test
    void findAllReturnsSavedRecords() {
        repository.save(parcel);

        assertEquals(1, repository.findAll().size());
        assertEquals(parcel, repository.findAll().get(0));
    }

    @Test
    void findMissingRecordReturnsEmpty() {
        assertTrue(repository.findById("P999").isEmpty());
    }

    @Test
    void deleteByIdRemovesRecord() {
        repository.save(parcel);

        assertTrue(repository.deleteById("P001"));
        assertEquals(0, repository.size());
    }

    @Test
    void duplicateKeyThrowsException() {
        repository.save(parcel);

        assertThrows(DataStorageException.class,
                () -> repository.save(parcel));
    }

    @Test
    void nullRecordThrowsException() {
        assertThrows(DataStorageException.class,
                () -> repository.save(null));
    }

    @Test
    void clearRemovesAllRecords() {
        repository.save(parcel);
        repository.clear();

        assertEquals(0, repository.size());
        assertTrue(repository.findAll().isEmpty());
    }
}