package seedu.reserve.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;


/**
 * Represents a Reservation's email in the reservation book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    private static final String SPECIAL_CHARACTERS = "+_.-";
    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). The local-part may not start or end with any special "
            + "characters. It must be at most 64 characters long.\n"
            + "2. This is followed by a '@' and then a domain name. The domain name is made up of domain labels "
            + "separated by periods.\n"
            + "The domain name must:\n"
            + "    - end with a domain label at least 2 characters long\n"
            + "    - have each domain label start and end with alphanumeric characters\n"
            + "    - must be at most 255 characters long\n"
            + "    - have each domain label consist of alphanumeric characters, separated only by hyphens, if any.";
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
