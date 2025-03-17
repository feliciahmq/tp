package seedu.reserve.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.commons.exceptions.DataLoadingException;
import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReadOnlyUserPrefs;
import seedu.reserve.model.UserPrefs;

/**
 * Manages storage of ReserveMate data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ReserveMateStorage reserveMateStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ReserveMateStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ReserveMateStorage reserveMateStorage, UserPrefsStorage userPrefsStorage) {
        this.reserveMateStorage = reserveMateStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ ReserveMate methods ==============================

    @Override
    public Path getReserveMateFilePath() {
        return reserveMateStorage.getReserveMateFilePath();
    }

    @Override
    public Optional<ReadOnlyReserveMate> readReserveMate() throws DataLoadingException {
        return readReserveMate(reserveMateStorage.getReserveMateFilePath());
    }

    @Override
    public Optional<ReadOnlyReserveMate> readReserveMate(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return reserveMateStorage.readReserveMate(filePath);
    }

    @Override
    public void saveReserveMate(ReadOnlyReserveMate reserveMate) throws IOException {
        saveReserveMate(reserveMate, reserveMateStorage.getReserveMateFilePath());
    }

    @Override
    public void saveReserveMate(ReadOnlyReserveMate reserveMate, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        reserveMateStorage.saveReserveMate(reserveMate, filePath);
    }

}
