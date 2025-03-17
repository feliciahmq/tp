package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.reserve.model.Model;
import seedu.reserve.model.ReserveMate;

/**
 * Clears the reservation book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Reservation book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setReserveMate(new ReserveMate());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
