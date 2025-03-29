package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.Model;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.reservation.Reservation;

/**
 * Clears the reservation book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Reservation book has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all reservations from the reservation book.\n"
            + "Parameters: cfm must be provided to confirm the action\n"
            + "Example: " + COMMAND_WORD + " cfm";
    public static final String MESSAGE_ADD_CONFIRM_CLEAR_RESERVATION =
            "Are you sure you want to clear ALL reservations? Type 'clear cfm'";
    public static final String MESSAGE_NO_RESERVATIONS_TO_CLEAR =
            "Reservation List is empty. No reservations found to clear!";

    private final boolean isConfirmed;

    /**
     * Constructs a {@code ClearCommand} with a confirmation flag.
     *
     * @param isConfirmed {@code true} if the user has confirmed the action, {@code false} otherwise.
     */
    public ClearCommand(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    /**
     * Validates the confirmation argument for clearing reservations.
     *
     * @param trimmedArgs The user input argument after trimming spaces.
     * @return {@code true} if the argument is valid, {@code false} otherwise.
     */
    public static boolean isValidConfirm(String trimmedArgs) {
        String[] trimmedArgArray = trimmedArgs.split("\\s+");
        if (trimmedArgArray.length > 1) {
            return false;
        }
        return true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!isConfirmed) {
            throw new CommandException(MESSAGE_ADD_CONFIRM_CLEAR_RESERVATION);
        }

        List<Reservation> lastShownList = model.getFilteredReservationList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_RESERVATIONS_TO_CLEAR);
        }

        assert (isConfirmed == true);
        model.setReserveMate(new ReserveMate());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClearCommand)) {
            return false;
        }
        ClearCommand otherCommand = (ClearCommand) other;
        return this.isConfirmed == otherCommand.isConfirmed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isConfirmed);
    }
}
