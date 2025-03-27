package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;

/**
 * View statistics of reservations in ReserveMate.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String SHOWING_STATISTICS_MESSAGE = "Displayed statistics of reservations.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(SHOWING_STATISTICS_MESSAGE, false, true, false);
    }
}
