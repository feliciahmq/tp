package seedu.reserve.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.reserve.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.reserve.commons.exceptions.IllegalValueException;
import seedu.reserve.commons.util.JsonUtil;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.testutil.TypicalReservation;

public class JsonSerializableReserveMateTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableReserveMateTest");
    private static final Path TYPICAL_RESERVATIONS_FILE =
            TEST_DATA_FOLDER.resolve("typicalReservationReserveMate.json");
    private static final Path INVALID_RESERVATION_FILE =
            TEST_DATA_FOLDER.resolve("invalidReservationReserveMate.json");
    private static final Path DUPLICATE_RESERVATION_FILE =
            TEST_DATA_FOLDER.resolve("duplicateReservationReserveMate.json");

    @Test
    public void toModelType_typicalReservationsFile_success() throws Exception {
        JsonSerializableReserveMate dataFromFile = JsonUtil.readJsonFile(TYPICAL_RESERVATIONS_FILE,
                JsonSerializableReserveMate.class).get();
        ReserveMate reserveMateFromFile = dataFromFile.toModelType();
        ReserveMate typicalReservationsReserveMate = TypicalReservation.getTypicalReserveMate();
        assertEquals(reserveMateFromFile, typicalReservationsReserveMate);
    }

    @Test
    public void toModelType_invalidReservationFile_throwsIllegalValueException() throws Exception {
        JsonSerializableReserveMate dataFromFile = JsonUtil.readJsonFile(INVALID_RESERVATION_FILE,
                JsonSerializableReserveMate.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateReservations_throwsIllegalValueException() throws Exception {
        JsonSerializableReserveMate dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RESERVATION_FILE,
                JsonSerializableReserveMate.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableReserveMate.MESSAGE_DUPLICATE_RESERVATION,
                dataFromFile::toModelType);
    }

}
