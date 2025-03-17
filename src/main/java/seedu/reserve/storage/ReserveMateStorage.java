package seedu.reserve.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.reserve.commons.exceptions.DataLoadingException;
import seedu.reserve.model.ReadOnlyReserveMate;

/**
 * Represents a storage for {@link seedu.reserve.model.ReserveMate}.
 */
public interface ReserveMateStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getReserveMateFilePath();

    /**
     * Returns ReserveMate data as a {@link ReadOnlyReserveMate}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyReserveMate> readReserveMate() throws DataLoadingException;

    /**
     * @see #getReserveMateFilePath()
     */
    Optional<ReadOnlyReserveMate> readReserveMate(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyReserveMate} to the storage.
     * @param reserveMate cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReserveMate(ReadOnlyReserveMate reserveMate) throws IOException;

    /**
     * @see #saveReserveMate(ReadOnlyReserveMate)
     */
    void saveReserveMate(ReadOnlyReserveMate reserveMate, Path filePath) throws IOException;

}
