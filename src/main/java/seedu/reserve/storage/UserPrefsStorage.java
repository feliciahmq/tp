package seedu.reserve.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.reserve.commons.exceptions.DataLoadingException;
import seedu.reserve.model.ReadOnlyUserPrefs;
import seedu.reserve.model.UserPrefs;

/**
 * Represents a storage for {@link seedu.reserve.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns UserPrefs data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from preference file failed.
     */
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    /**
     * Saves the given {@link seedu.reserve.model.ReadOnlyUserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

}
