package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.reserve.logic.commands.HelpCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code HelpCommand} object.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code HelpCommand} object for execution.
     *
     * @param args The user input arguments for the help command.
     * @return A new {@code HelpCommand} instance if the input is valid.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public HelpCommand parse(String args) throws ParseException {
        try {
            boolean isValid = HelpCommand.isValidHelpCommand(args);
            if (!isValid) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
            return new HelpCommand();
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), pe);
        }
    }
}
