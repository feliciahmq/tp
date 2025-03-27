package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;

/**
 * View statistics of reservations in ReserveMate.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String SUMMARY_MESSAGE = "Displayed summary statistics of reservations.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(SUMMARY_MESSAGE, false, true, false);
    }
}
