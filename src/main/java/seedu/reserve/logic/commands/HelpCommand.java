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
        + "3. pref - Saves or displays a reservation preference \n"
        + "4. delete - Delete a reservation \n"
        + "5. show - Display reservation details \n"
        + "6. list - Display a list of all reservations \n"
        + "7. help - Display a list of available commands \n"
        + "8. find - Find reservations by names \n"
        + "9. stats - Display reservation statistics \n"
        + "10. free - Display all free time slots to the user \n"
        + "11. filter - Filters all reservations which are between the two dates provided by the user \n"
        + "12. clear - Deletes all contacts \n"
        + "13. exit - Exit the program";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, false, false, false);
    }
}
