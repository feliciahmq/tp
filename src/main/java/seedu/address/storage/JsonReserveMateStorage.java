package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyReserveMate;

/**
 * A class to access ReserveMate data stored as a json file on the hard disk.
 */
public class JsonReserveMateStorage implements ReserveMateStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonReserveMateStorage.class);

    private Path filePath;

    public JsonReserveMateStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getReserveMateFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyReserveMate> readReserveMate() throws DataLoadingException {
        return readReserveMate(filePath);
    }

    /**
     * Similar to {@link #readReserveMate()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyReserveMate> readReserveMate(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableReserveMate> jsonReserveMate = JsonUtil.readJsonFile(
                filePath, JsonSerializableReserveMate.class);
        if (!jsonReserveMate.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonReserveMate.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveReserveMate(ReadOnlyReserveMate ReserveMate) throws IOException {
        saveReserveMate(ReserveMate, filePath);
    }

    /**
     * Similar to {@link #saveReserveMate(ReadOnlyReserveMate)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveReserveMate(ReadOnlyReserveMate ReserveMate, Path filePath) throws IOException {
        requireNonNull(ReserveMate);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableReserveMate(ReserveMate), filePath);
    }

}
