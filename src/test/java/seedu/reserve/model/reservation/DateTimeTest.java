package seedu.reserve.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DateTimeTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final String INVALID_DATE_TIME_FORMAT_STR = "2025-12-12 180";

    private String formattedYesterdayDateTime;
    private String formattedTomorrowDateTime;
    private String formattedDayAfterDateTime;


    /**
     * Helper method to generate a formatted date string based on the current date and time.
     *
     * @param daysToAdd The number of days to add or subtract from the current date.
     * @return A formatted date string in the format "yyyy-MM-dd HHmm".
     */
    private String getFormattedDateTime(int daysToAdd) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetDateTime = now.plusDays(daysToAdd);
        return targetDateTime.format(FORMATTER);
    }

    @Test
    public void constructor_validDateTime_success() {
        assertEquals(DateTime.class, DateTime.fromFileString(formattedYesterdayDateTime).getClass());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime(null));
    }

    @Test
    public void constructor_invalidDateTime_throwsIllegalArgumentException() {
        String invalidDateTime = "2023-12-12 190";
        assertThrows(IllegalArgumentException.class, () -> new DateTime(invalidDateTime));
    }

    @Test
    public void constructor_invalidFileInputDateTime_throwsIllegalValueException() {
        String invalidDateTime = "2023-12-12 190";
        assertThrows(IllegalArgumentException.class, () -> DateTime.fromFileString(invalidDateTime));
    }

    @BeforeEach
    public void setUp() {
        formattedYesterdayDateTime = getFormattedDateTime(-1);
        formattedTomorrowDateTime = getFormattedDateTime(1);
        formattedDayAfterDateTime = getFormattedDateTime(2);
    }

    @Test
    public void isValidDateTime() {

        // invalid date time
        assertFalse(DateTime.isValidDateTime(""));
        assertFalse(DateTime.isValidDateTime("1990-12-12 1800"));
        assertFalse(DateTime.isValidDateTime("2020-12-12 1800"));
        assertFalse(DateTime.isValidDateTime(INVALID_DATE_TIME_FORMAT_STR));
        assertFalse(DateTime.isValidDateTime(formattedYesterdayDateTime));

        // valid date time
        assertTrue(DateTime.isValidDateTime("2030-12-12 1800"));
        assertTrue(DateTime.isValidDateTime(formattedTomorrowDateTime));

    }

    @Test
    public void isValidFileInputDateTime() {
        // invalid date time
        assertFalse(DateTime.isValidFileInputDateTime(""));
        assertFalse(DateTime.isValidFileInputDateTime(INVALID_DATE_TIME_FORMAT_STR));

        // valid date time
        assertTrue(DateTime.isValidFileInputDateTime(formattedYesterdayDateTime));
        assertTrue(DateTime.isValidFileInputDateTime(formattedTomorrowDateTime));

    }

    @Test
    public void equals() {
        DateTime dateTime = new DateTime(formattedTomorrowDateTime);

        // same date and time value -> return true
        assertTrue(dateTime.equals(new DateTime(formattedTomorrowDateTime)));

        // same DateTime object -> return true
        assertTrue(dateTime.equals(dateTime));

        // null date and time -> return false
        assertFalse(dateTime.equals(null));

        // different types -> return false
        assertFalse(dateTime.equals(5));

        // different date and time value -> return false
        assertFalse(dateTime.equals(new DateTime(formattedDayAfterDateTime)));
    }
}
