package seedu.reserve.model.reservation;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Reservation's preference in the reserve book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPreference(String)}
 */
public class Preference {

    public static final String MESSAGE_CONSTRAINTS = "Preference text cannot exceed 50 characters.";
    public static final int MAX_LENGTH = 50;

    private final String preferenceText;

    /**
     * Constructs a {@code Preference}.
     *
     * @param preference A valid preference.
     */
    public Preference(String preference) {
        requireNonNull(preference);
        preferenceText = preference;
    }

    /**
     * Returns true if a given string is a valid preference.
     */
    public static boolean isValidPreference(String test) {
        return test != null && test.length() <= MAX_LENGTH;
    }

    /**
     * Returns true if this preference is empty.
     */
    public boolean isEmpty() {
        return preferenceText.isEmpty();
    }

    @Override
    public String toString() {
        return preferenceText;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Preference)) {
            return false;
        }

        Preference otherPreference = (Preference) other;
        return preferenceText.equals(otherPreference.preferenceText);
    }

    @Override
    public int hashCode() {
        return preferenceText.hashCode();
    }
}
