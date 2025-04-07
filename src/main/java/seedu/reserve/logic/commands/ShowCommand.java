package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX;

import java.util.List;
import java.util.logging.Logger;

import seedu.reserve.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(ShowCommand.class);

    private final Index targetIndex;

    /**
     * Constructs a ShowCommand for the specified index.
     *
     * @param targetIndex The index to specify which reservation's details to show.
     */
    public ShowCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        logger.fine("Created new ShowCommand with target index: " + targetIndex.getOneBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing ShowCommand for index: " + targetIndex.getOneBased());
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Invalid index provided: " + targetIndex.getOneBased()
                    + " (List size: " + lastShownList.size() + ")");
            throw new CommandException(MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToShow = lastShownList.get(targetIndex.getZeroBased());
        logger.info("Successfully retrieved reservation: " + reservationToShow);
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
