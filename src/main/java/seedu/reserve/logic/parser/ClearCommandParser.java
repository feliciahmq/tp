package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.reserve.logic.commands.ClearCommand;
import seedu.reserve.logic.commands.Command;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ClearCommand} object.
 */
public class ClearCommandParser {

    /**
     * Parses the given {@code String} of arguments and returns a {@code ClearCommand} object for execution.
     *
     * @param args The input arguments provided by the user.
     * @return A {@code ClearCommand} object with the parsed confirmation status.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public Command parse(String args) throws ParseException {
        try {
            boolean isConfirmed = ParserUtil.parseClearArgs(args);
            return new ClearCommand(isConfirmed);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE), pe);
        }
    }
}
