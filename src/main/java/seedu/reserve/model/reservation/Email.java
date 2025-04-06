package seedu.reserve.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;


/**
 * Represents a Reservation's email in the reservation book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should follow the format local-part@domain and meet the following rules:\n"
            + "1. The local-part may contain alphanumeric characters and these special characters (excluding parentheses): "
            + SPECIAL_CHARACTERS + ". It must not start or end with a special character, and be at most 64 characters long.\n"
            + "2. The domain must consist of labels separated by periods (e.g., 'example.com'), with the following:\n"
            + "   - Each label must start and end with an alphanumeric character\n"
            + "   - Labels may contain hyphens, but no other special characters\n"
            + "   - The domain must be at most 255 characters long\n"
            + "   - The final label (e.g., '.com','.sg') must be at least 2 characters long and contain only letters (no digits)";


    // alphanumeric and special characters
    private static final String ALPHANUMERIC = "[a-zA-Z0-9]+"; // Alphanumeric (no underscores)
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC + ")*";
    private static final String DOMAIN_LABEL_REGEX = ALPHANUMERIC + "(-" + ALPHANUMERIC + ")*";
    private static final String DOMAIN_REGEX = "(" + DOMAIN_LABEL_REGEX + "\\.)+" + "[a-zA-Z]{2,}$"; // TLD min 2 chars
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        int domainIndex = test.indexOf("@");
        assert domainIndex != -1;
        // Check the lengths of locale and domain parts
        String domain = test.substring(domainIndex + 1);
        String locale = test.substring(0, domainIndex);

        if (locale.length() > 64) {
            return false;
        }

        if (domain.length() > 255) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Email)) {
            return false;
        }

        Email otherEmail = (Email) other;
        return value.equals(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
