package seedu.reserve.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DateTimeTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final String INVALID_DATE_TIME_FORMAT_STR = "2025-12-12 180";
    private static final String INVALID_DATE_TIME = "2025-02-29 1800";
    private static final String INVALID_DATE_TIME_NON_HOURLY = "2025-12-12 1830";
    private static final DateTime endDate = DateTime.fromFileString("2025-12-15 1800");
    private static final DateTime middleDate = DateTime.fromFileString("2025-12-13 1400");
    private static final DateTime outOfBoundsDate = DateTime.fromFileString("2025-12-16 1800");
    private static final DateTime startDate = DateTime.fromFileString("2025-12-12 1800");

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
        LocalDateTime targetDateTime = now.truncatedTo(ChronoUnit.HOURS).plusDays(daysToAdd);
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
        assertFalse(DateTime.isValidDateTime("1990-12-12 1800")); // Past Date
        assertFalse(DateTime.isValidDateTime("2020-12-12 1800")); // Past Date
        assertFalse(DateTime.isValidDateTime("2025-12-12 1830")); // More than 60 days away and not hourly
        assertFalse(DateTime.isValidDateTime(INVALID_DATE_TIME_FORMAT_STR));
        assertFalse(DateTime.isValidDateTime(formattedYesterdayDateTime));

        // valid date time
        assertTrue(DateTime.isValidDateTime(getFormattedDateTime(29)));
        assertTrue(DateTime.isValidDateTime(formattedTomorrowDateTime));

    }

    @Test
    public void isInvalidDateTime() {
        assertFalse(DateTime.isValidDateTime(INVALID_DATE_TIME));

        assertThrows(IllegalArgumentException.class, () -> new DateTime(INVALID_DATE_TIME));
        assertThrows(IllegalArgumentException.class, () -> DateTime.fromFileString(INVALID_DATE_TIME));
    }

    @Test
    public void isValidFileInputDateTime() {
        // invalid date time
        assertFalse(DateTime.isValidFileInputDateTime(""));
        assertFalse(DateTime.isValidFileInputDateTime(INVALID_DATE_TIME_FORMAT_STR));
        assertFalse(DateTime.isValidFileInputDateTime(INVALID_DATE_TIME_NON_HOURLY));

        // valid date time
        assertTrue(DateTime.isValidFileInputDateTime(formattedYesterdayDateTime));
        assertTrue(DateTime.isValidFileInputDateTime(formattedTomorrowDateTime));
        assertEquals(DateTime.class, DateTime.fromFileString(formattedYesterdayDateTime).getClass());

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

    @Test
    public void isBetween() {
        // date in between both start and end date
        assertTrue(middleDate.isBetween(startDate, endDate));

        // date out of bounds
        assertFalse(outOfBoundsDate.isBetween(startDate, endDate));

        // edge dates
        assertTrue(startDate.isBetween(startDate, endDate));
        assertTrue(endDate.isBetween(startDate, endDate));
    }

}
