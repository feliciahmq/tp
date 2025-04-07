package seedu.reserve.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX;

import java.util.logging.Logger;

import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.commands.ShowCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {
    private static final Logger logger = LogsCenter.getLogger(ShowCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns a ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.info("Parsing ShowCommand with arguments: " + args);
        if (args.trim().isEmpty()) {
            logger.warning("Empty input detected for ShowCommand");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            logger.info("Successfully parsed index: " + index.getOneBased());
            return new ShowCommand(index);
        } catch (ParseException pe) {
            logger.warning("Failed to parse index from input: '" + args + "' - " + pe.getMessage());
            throw new ParseException(
                    MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX, pe);
        }
    }
}
