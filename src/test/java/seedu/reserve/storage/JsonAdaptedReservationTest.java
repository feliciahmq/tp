package seedu.reserve.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.reserve.storage.JsonAdaptedReservation.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalReservation.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.reserve.commons.exceptions.IllegalValueException;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;

public class JsonAdaptedReservationTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_OCCASION = "#friend";
    private static final String INVALID_DINERS = "0";
    private static final String INVALID_DATETIME = "2020-01-01 180";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_DINERS = BENSON.getDiners().toString();
    private static final String VALID_DATETIME = BENSON.getDateTime().toString();
    private static final List<JsonAdaptedOccasion> VALID_OCCASIONS = BENSON.getOccasions().stream()
            .map(JsonAdaptedOccasion::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validReservationDetails_returnsReservation() throws Exception {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(BENSON);
        assertEquals(BENSON, reservation.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_DINERS,
                        VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(null, VALID_PHONE, VALID_EMAIL,
                VALID_DINERS, VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                        VALID_DINERS, VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(VALID_NAME, null, VALID_EMAIL,
                VALID_DINERS, VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                        VALID_DINERS, VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(VALID_NAME, VALID_PHONE, null,
                VALID_DINERS, VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidDiners_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        INVALID_DINERS, VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = Diners.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullDiners_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Diners.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_DINERS, INVALID_DATETIME, VALID_OCCASIONS);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedReservation reservation = new JsonAdaptedReservation(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_DINERS, null, VALID_OCCASIONS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, reservation::toModelType);
    }

    @Test
    public void toModelType_invalidOccasions_throwsIllegalValueException() {
        List<JsonAdaptedOccasion> invalidOccasions = new ArrayList<>(VALID_OCCASIONS);
        invalidOccasions.add(new JsonAdaptedOccasion(INVALID_OCCASION));
        JsonAdaptedReservation reservation =
                new JsonAdaptedReservation(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_DINERS, VALID_DATETIME, invalidOccasions);
        assertThrows(IllegalValueException.class, reservation::toModelType);
    }

}
