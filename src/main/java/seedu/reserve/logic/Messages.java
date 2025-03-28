package seedu.reserve.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.reserve.logic.parser.Prefix;
import seedu.reserve.model.reservation.Reservation;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX =
            "The reservation index provided is invalid";
    public static final String MESSAGE_RESERVATIONS_LISTED_OVERVIEW = "%1$d reservations listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EMPTY_RESERVATION_LIST =
            "No reservations found. Use the 'add' command to create a reservation\n"
                    + "or 'help' to view all commands.";
    public static final String MESSAGE_NO_RESERVATIONS = "No reservations found.";

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
     * Formats the {@code Reservation} for display to the user.
     */
    public static String format(Reservation reservation) {
        final StringBuilder builder = new StringBuilder();
        builder.append(reservation.getName())
                .append("; Phone: ")
                .append(reservation.getPhone())
                .append("; Email: ")
                .append(reservation.getEmail())
                .append("; Number of Diners: ")
                .append(reservation.getDiners().value)
                .append("; Occasion: ");
        reservation.getTags().forEach(builder::append);
        return builder.toString();
    }

}
