package seedu.reserve.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.reserve.commons.exceptions.DataLoadingException;
import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReadOnlyUserPrefs;
import seedu.reserve.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ReserveMateStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getReserveMateFilePath();

    @Override
    Optional<ReadOnlyReserveMate> readReserveMate() throws DataLoadingException;

    @Override
    void saveReserveMate(ReadOnlyReserveMate reserveMate) throws IOException;

}
