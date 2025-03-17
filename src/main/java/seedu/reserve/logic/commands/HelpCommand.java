package seedu.reserve.logic.commands;

import seedu.reserve.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help"; //

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "List of Commands: \n"
        + "1. add - Enter a reservation \n"
        + "2. delete - Delete a reservation \n"
        + "3. show - Display reservation details \n"
        + "4. list - Display a list of all reservations \n"
        + "5. help - Display the available commands \n"
        + "6. find - Finds a reservation by name \n";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
