package seedu.reserve.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Represents a Date and Time for a reservation.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime must be in the format YYYY-MM-DD HHmm "
            + "and be a date-time after the current time.";
    public static final String VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2} \\d{4}$";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

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

    /**
     * Returns true if a given string is a valid date-time in the format YYYY-MM-DD HHmm.
     */
    public static boolean isValidDateTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            return isAfterCurrentTime(LocalDateTime.parse(test, FORMATTER));
        } catch (DateTimeParseException | ParseException e) {
            return false;
        }
    }

    /**
     * Returns true if the given DateTime is after the current time.
     */
    public static boolean isAfterCurrentTime(LocalDateTime dateTime) throws ParseException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return dateTime.isAfter(currentDateTime);
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
}
