package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import seedu.reserve.model.Model;

/**
 * Lists all reservations in the reservation book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all reservations";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
