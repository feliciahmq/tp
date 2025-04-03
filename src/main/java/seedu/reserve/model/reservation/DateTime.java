package seedu.reserve.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;

import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Represents a Date and Time for a reservation.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime implements Comparable<DateTime> {

    public static final String MESSAGE_CONSTRAINTS = "DateTime should be of the format YYYY-MM-DD HHmm "
            + "and adhere to the following constraints: \n"
            + "1. The date must be a valid calendar date. \n"
            + "2. The time must be in hourly increments (e.g., 0000, 0100, etc.). \n"
            + "3. The date-time must be after the current time but within 60 days from now.";
    public static final String MESSAGE_CONSTRAINTS_FREE = "Date must be in the format YYYY-MM-DD "
            + "and adhere to the following constraints: \n"
            + "1. The date must be a valid calendar date. \n"
            + "2. The date must be after the current date but within 60 days from now.";
    public static final String MESSAGE_CONSTRAINTS_FILTER = "DateTime must be in the format YYYY-MM-DD HHmm, "
            + "must be a valid calendar date and the time must be in hourly increments. \n";
    public static final String VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2} \\d{4}$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final LocalDateTime currentDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

    public final LocalDateTime value;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid date-time string.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.value = LocalDateTime.parse(dateTime, FORMATTER);
    }

    private DateTime(LocalDateTime dateTime) {
        this.value = dateTime;
    }

    /**
     * Constructs a {@code DateTime} from a string input without validation.
     *
     * @param dateTime A date-time string.
     * @return A DateTime object if the string is valid, otherwise null.
     */
    public static DateTime fromFileString(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidFileInputDateTime(dateTime), MESSAGE_CONSTRAINTS);
        return new DateTime(LocalDateTime.parse(dateTime, FORMATTER));
    }

    /**
     * Returns true if a given string is a valid date-time in the format YYYY-MM-DD HHmm and after current date-time.
     */
    public static boolean isValidDateTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            if (!isHourlyTiming(test)) {
                return false;
            }

            LocalDateTime parsedDateTime = LocalDateTime.parse(test, FORMATTER);
            return isAfterCurrentTime(parsedDateTime) && isBeforeMaxBookingTime(parsedDateTime);
        } catch (DateTimeParseException | ParseException e) {
            return false;
        }
    }

    /**
     * Returns true if the time of the date-time string ends with "00".
     *
     * @param time The date-time string in format YYYY-MM-DD HHmm
     * @return true if minutes are 00, false otherwise
     */
    private static boolean isHourlyTiming(String time) {
        int minutesIndex = time.length() - 2;
        String minutes = time.substring(minutesIndex);
        return minutes.endsWith("00");
    }

    /**
     * Returns true if a given string from an input file a valid date-time in the format YYYY-MM-DD HHmm.
     */
    public static boolean isValidFileInputDateTime(String test) {
        return test.matches(VALIDATION_REGEX) && isHourlyTiming(test);
    }

    /**
     * Returns true if the given DateTime is after the current time.
     */
    public static boolean isAfterCurrentTime(LocalDateTime dateTime) throws ParseException {
        return dateTime.isAfter(currentDateTime);
    }

    /**
     * Returns true if the given DateTime is before 60 days from the current time.
     */
    public static boolean isBeforeMaxBookingTime(LocalDateTime dateTime) throws ParseException {
        LocalDateTime maxBookingDateTime = currentDateTime.plusDays(60);
        assert(maxBookingDateTime.isBefore(currentDateTime.plusDays(61)));
        return dateTime.isBefore(maxBookingDateTime) || dateTime.isEqual(maxBookingDateTime);
    }

    @Override
    public String toString() {
        return value.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return value.equals(otherDateTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(DateTime dateTime) {
        return this.value.compareTo(dateTime.value);
    }

    /**
     * Returns True if the current {@code DateTime} object's date and time is between the start and end
     * {@code DateTime} objects (inclusive). The {@code startDateTime} must be before {@end endDateTime}.
     *
     * @param startDateTime start date
     * @param endDateTime end date
     * @return true if conditions met, false otherwise
     */
    public boolean isBetween(DateTime startDateTime, DateTime endDateTime) {
        return this.compareTo(startDateTime) >= 0 && this.compareTo(endDateTime) <= 0;
    }
}
