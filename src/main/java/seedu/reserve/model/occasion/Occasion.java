package seedu.reserve.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the reservation book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Occasion {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String occasionName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Occasion(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.occasionName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
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
