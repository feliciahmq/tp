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
        + "2. edit - Edit a reservation \n"
        + "3. delete - Delete a reservation \n"
        + "4. show - Display reservation details \n"
        + "5. list - Display a list of all reservations \n"
        + "6. help - Display list of available commands \n"
        + "7. find - Finds a reservation by name \n"
        + "8. clear - Deletes all contacts \n"
        + "9. exit - Exit the program";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, false);
    }
}
