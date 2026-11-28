package ua.lpnu.kzp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Узагальнене сховище об'єктів із пошуком за ключем.
 *
 * @param <T> тип об'єктів сховища
 */
public final class Repository<T> {

    private final Map<String, T> storage = new LinkedHashMap<>();
    private final Function<T, String> keyExtractor;

    public Repository(Function<T, String> keyExtractor) {
        if (keyExtractor == null) {
            throw new DataStorageException(
                    "Функція отримання ключа не може бути null");
        }

        this.keyExtractor = keyExtractor;
    }

    public void save(T item) {
        if (item == null) {
            throw new DataStorageException(
                    "Запис не може бути null");
        }

        String key = keyExtractor.apply(item);

        if (key == null || key.isBlank()) {
            throw new DataStorageException(
                    "Ключ запису не може бути порожнім");
        }

        if (storage.containsKey(key)) {
            throw new DataStorageException(
                    "Запис із таким ключем вже існує: " + key);
        }

        storage.put(key, item);
    }

    public Optional<T> findById(String key) {
        return Optional.ofNullable(storage.get(key));
    }

    public List<T> findAll() {
        return List.copyOf(storage.values());
    }

    public boolean deleteById(String key) {
        return storage.remove(key) != null;
    }

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }
}