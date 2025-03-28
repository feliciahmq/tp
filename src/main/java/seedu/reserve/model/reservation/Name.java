package seedu.reserve.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.AppUtil.checkArgument;

/**
 * Represents a Reservation's name in the reservation book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final int MAX_NAME_LENGTH = 50;
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain characters and spaces, should not be blank and not more than 50 characters.";
    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";
    public static final String WORDS_REGEX = "\\s+";
    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = formatName(name);
    }

    /**
     * Formats the given name by capitalising the first letter of each word
     * and converting the rest to lowercase.
     *
     * @param name The user input name string.
     * @return A formatted name string.
     */
    private String formatName(String name) {
        StringBuilder formattedName = new StringBuilder();
        String[] nameLowerCase = name.toLowerCase().split(WORDS_REGEX);

        for(String word : nameLowerCase) {
            formattedName.append(word.toUpperCase().charAt(0));
            formattedName.append(word.substring(1));
            formattedName.append(" ");
        }
        return formattedName.toString().trim();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {

        if (isValidNameLength(test)) {
            return test.matches(VALIDATION_REGEX);
        }
        return false;
    }

    /**
     * Returns true if a given string is less than 50 characters.
     */
    public static boolean isValidNameLength(String test) {
        return test.length() <= MAX_NAME_LENGTH;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        String otherFullNameLowerCase = otherName.fullName.toLowerCase();
        String thisFullName = fullName.toLowerCase();
        return thisFullName.equals(otherFullNameLowerCase);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
