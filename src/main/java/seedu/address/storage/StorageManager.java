package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyReserveMate;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ReserveMate data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ReserveMateStorage ReserveMateStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ReserveMateStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(ReserveMateStorage ReserveMateStorage, UserPrefsStorage userPrefsStorage) {
        this.ReserveMateStorage = ReserveMateStorage;
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
        return ReserveMateStorage.getReserveMateFilePath();
    }

    @Override
    public Optional<ReadOnlyReserveMate> readReserveMate() throws DataLoadingException {
        return readReserveMate(ReserveMateStorage.getReserveMateFilePath());
    }

    @Override
    public Optional<ReadOnlyReserveMate> readReserveMate(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return ReserveMateStorage.readReserveMate(filePath);
    }

    @Override
    public void saveReserveMate(ReadOnlyReserveMate ReserveMate) throws IOException {
        saveReserveMate(ReserveMate, ReserveMateStorage.getReserveMateFilePath());
    }

    @Override
    public void saveReserveMate(ReadOnlyReserveMate ReserveMate, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        ReserveMateStorage.saveReserveMate(ReserveMate, filePath);
    }

}
