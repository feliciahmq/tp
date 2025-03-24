package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.logic.parser.ParserUtil;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Reservation;

/**
 * Deletes a reservation identified using it's displayed index from the reservation book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the reservation identified by the index number used in the reservation list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 cfm";

    public static final String MESSAGE_DELETE_RESERVATION_SUCCESS = "Reservation %1$s deleted successfully";
    public static final String MESSAGE_CONFIRM_DELETE = "Are you sure you want to delete %1$s? Type 'delete %1$s cfm'";
    private static final int INDEX_POSITION = 0;

    private final Index targetIndex;
    private final boolean isConfirmed;


    /**
     * Constructs a DeleteCommand with the specified target index and confirmation status.
     *
     * @param targetIndex The index of the item to be deleted.
     * @param isConfirmed A boolean indicating whether the deletion is confirmed.
     */
    public DeleteCommand(Index targetIndex, boolean isConfirmed) {
        this.targetIndex = targetIndex;
        this.isConfirmed = isConfirmed;

    }

    /**
     * Checks if the given delete command arguments are valid.
     * A valid delete command should contain at least one argument, which is a valid index.
     *
     * @param trimmedArgs The trimmed string containing the delete command arguments.
     * @return {@code true} if the delete command is valid, {@code false} otherwise.
     * @throws ParseException If the index is not a valid non-zero unsigned integer.
     */
    public static boolean isValidDelete(String trimmedArgs) throws ParseException {
        String[] trimmedArgArray = trimmedArgs.split("\\s+");
        if (trimmedArgArray[INDEX_POSITION].isEmpty()) {
            return false;
        }
        // Check for valid Index
        ParserUtil.parseIndex(trimmedArgArray[INDEX_POSITION]);
        return true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (!isConfirmed) {
            return new CommandResult(String.format(MESSAGE_CONFIRM_DELETE, this.targetIndex.getOneBased()));
        }

        model.deleteReservation(reservationToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_RESERVATION_SUCCESS, this.targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
