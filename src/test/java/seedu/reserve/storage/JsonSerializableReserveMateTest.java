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
    private static final Path TYPICAL_CUSTOMERS_FILE =
            TEST_DATA_FOLDER.resolve("typicalReservationReserveMate.json");
    private static final Path INVALID_CUSTOMER_FILE =
            TEST_DATA_FOLDER.resolve("invalidReservationReserveMate.json");
    private static final Path DUPLICATE_CUSTOMER_FILE =
            TEST_DATA_FOLDER.resolve("duplicateReservationReserveMate.json");

    @Test
    public void toModelType_typicalCustomersFile_success() throws Exception {
        JsonSerializableReserveMate dataFromFile = JsonUtil.readJsonFile(TYPICAL_CUSTOMERS_FILE,
                JsonSerializableReserveMate.class).get();
        ReserveMate reserveMateFromFile = dataFromFile.toModelType();
        ReserveMate typicalCustomersReserveMate = TypicalReservation.getTypicalReserveMate();
        assertEquals(reserveMateFromFile, typicalCustomersReserveMate);
    }

    @Test
    public void toModelType_invalidCustomerFile_throwsIllegalValueException() throws Exception {
        JsonSerializableReserveMate dataFromFile = JsonUtil.readJsonFile(INVALID_CUSTOMER_FILE,
                JsonSerializableReserveMate.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateCustomers_throwsIllegalValueException() throws Exception {
        JsonSerializableReserveMate dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CUSTOMER_FILE,
                JsonSerializableReserveMate.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableReserveMate.MESSAGE_DUPLICATE_RESERVATION,
                dataFromFile::toModelType);
    }

}
