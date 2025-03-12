package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpListCommand extends Command {

    public static final String COMMAND_WORD = "helplist"; //

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "List of Commands: \n"
        + "1. add - Enter a reservation \n"
        + "2. delete - Delete a reservation \n "
        + "3. show - Display reservation details \n "
        + "4. view_schedule - Check the schedule of reservations \n "
        + "5. list - Display the available commands \n";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
