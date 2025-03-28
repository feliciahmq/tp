package seedu.reserve.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of diners in a reservation.
 * Guarantees: immutable; is valid as declared in {@link #isValidDiners(String)}
 */
public class Diners {

    public static final String MESSAGE_CONSTRAINTS = "Number of diners must be between 1 and 10.";
    public static final int MIN_DINERS = 1;
    public static final int MAX_DINERS = 10;

    public final String value;

    /**
     * Constructs a {@code Diners}.
     *
     * @param numberOfDiners A valid number of diners.
     */
    public Diners(String numberOfDiners) {
        requireNonNull(numberOfDiners);
        checkArgument(isValidDiners(numberOfDiners), MESSAGE_CONSTRAINTS);
        value = formatDiners(numberOfDiners);
    }


    /**
     * Format the diners string by removing leading zeros.
     */
    private static String formatDiners(String diners) {
        try {
            return String.valueOf(Integer.parseInt(diners));
        } catch (NumberFormatException e) {
            return diners;
        }
    }

    /**
     * Returns true if a given number is a valid number of diners.
     */
    public static boolean isValidDiners(String test) {
        try {
            int number = Integer.parseInt(test);
            return number >= MIN_DINERS && number <= MAX_DINERS;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Diners)) {
            return false;
        }

        Diners otherDiners = (Diners) other;
        return value.equals(otherDiners.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
