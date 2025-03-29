package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.util.List;

import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Reservation;

/**
 * Lists all reservations in the reservation book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all reservations";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all reservations in the reservation book.\n"
            + "Example: " + COMMAND_WORD;

    /**
     * Validates whether the input arguments for the ListCommand are correct.
     *
     * @param args The input arguments provided by the user.
     * @return {@code true} if the arguments are valid (empty input), {@code false} otherwise.
     */
    public static boolean isValidListCommand(String args) {
        String trimmedArgs = args.trim();
        return trimmedArgs.isEmpty();
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);

        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_RESERVATION_LIST);
        }

        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        return other instanceof ListCommand;
    }

}
