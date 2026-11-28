package ua.lpnu.kzp;

/**
 * Власний виняток для обробки помилок сховища даних.
 */
public class DataStorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataStorageException(String message) {
        super(message);
    }

    public DataStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}