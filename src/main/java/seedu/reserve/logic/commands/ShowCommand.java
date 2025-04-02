package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX;

import java.util.List;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Reservation;

/**
 * Show a reservation details using it's displayed index from the reservation book
 */
public class ShowCommand extends Command {
    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the reservation details identified by the index number "
            + "used in the displayed reservation list.\n\n"
            + "Parameters: INDEX (must be a positive integer) and shown in the list\n\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_RESERVATION_SUCCESS = "Details of Reservation:\n%1$s";

    private final Index targetIndex;

    public ShowCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToShow = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SHOW_RESERVATION_SUCCESS, Messages.format(reservationToShow)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowCommand)) {
            return false;
        }

        ShowCommand otherShowCommand = (ShowCommand) other;
        return targetIndex.equals(otherShowCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
