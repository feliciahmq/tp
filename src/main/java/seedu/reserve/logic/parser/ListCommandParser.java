package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.reserve.logic.commands.ListCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ListCommand} object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code ListCommand} object for execution.
     *
     * @param args The user input arguments for the list command.
     * @return A new {@code ListCommand} instance if the input is valid.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public ListCommand parse(String args) throws ParseException {
        try {
            boolean isValid = ListCommand.isValidListCommand(args);
            if (!isValid) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            return new ListCommand();
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), pe);
        }
    }
}
