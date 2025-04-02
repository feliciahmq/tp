package seedu.reserve.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.reserve.logic.parser.Prefix;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.Reservation;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX =
            "The reservation index must be within the reservation list range";
    public static final String MESSAGE_RESERVATIONS_LISTED_OVERVIEW = "%1$d reservations listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EMPTY_RESERVATION_LIST =
            "No reservations found. Use the 'add' command to create a reservation\n"
                    + "or 'help' to view all commands.";
    public static final String MESSAGE_NO_RESERVATIONS = "No reservations found.";
    public static final String MESSAGE_DUPLICATE_RESERVATION =
            "A reservation already exists for this customer (same email or phone) at the chosen date-time.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code Reservation} for display to the user with each detail on a new line.
     */
    public static String format(Reservation reservation) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(reservation.getName().fullName).append("\n")
                .append("Phone: ")
                .append(reservation.getPhone().value).append("\n")
                .append("Email: ")
                .append(reservation.getEmail().value).append("\n")
                .append("Number of Diners: ")
                .append(reservation.getDiners().value).append("\n")
                .append("Date/Time: ")
                .append(reservation.getDateTime().toString()).append("\n")
                .append("Preference: ")
                .append(reservation.getPreference()).append("\n")
                .append("Occasion: ")
                .append(reservation.getOccasions().stream()
                        .map(Occasion::toString)
                        .collect(Collectors.joining(", ")));
        return builder.toString();
    }
}
