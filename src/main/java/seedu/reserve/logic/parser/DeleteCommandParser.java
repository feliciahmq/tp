package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX;

import javafx.util.Pair;
import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.commands.DeleteCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {

            Pair<Index, Boolean> parsedArgs = ParserUtil.parseDeleteArgs(args);

            return new DeleteCommand(parsedArgs.getKey(), parsedArgs.getValue());

        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX, pe);
        }
    }

}
