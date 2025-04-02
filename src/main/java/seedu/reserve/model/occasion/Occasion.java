package seedu.reserve.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_OCCASION;

/**
 * Represents an Occasion in the reservation book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidOccasionName(String)}
 */
public class Occasion {

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String MESSAGE_OCCASION_CONSTRAINTS = "Please indicate an occasion: \n"
        + "Example: " + PREFIX_OCCASION + "Birthday \n"
        + "Example: " + PREFIX_OCCASION + "None \n"
        + "Occasion name must not be empty and must be alphanumeric";

    public final String occasionName;

    /**
     * Constructs a {@code Occasion}.
     *
     * @param occasionName A valid occasion name.
     */
    public Occasion(String occasionName) {
        requireNonNull(occasionName);
        checkArgument(isValidOccasionName(occasionName), MESSAGE_OCCASION_CONSTRAINTS);
        this.occasionName = occasionName;
    }

    /**
     * Returns true if a given string is a valid occasion name.
     */
    public static boolean isValidOccasionName(String test) {
        return test.matches(VALIDATION_REGEX) && (test.length() >= 2 && test.length() <= 50);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Occasion)) {
            return false;
        }

        Occasion otherOccasion = (Occasion) other;
        return occasionName.equals(otherOccasion.occasionName);
    }

    @Override
    public int hashCode() {
        return occasionName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + occasionName + ']';
    }

}
