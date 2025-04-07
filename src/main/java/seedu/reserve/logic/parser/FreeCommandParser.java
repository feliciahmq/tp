package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.logging.Logger;

import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.logic.commands.FreeCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.reservation.DateTime;

/**
 * Parses input arguments and creates a new FreeCommand object
 */
public class FreeCommandParser implements Parser<FreeCommand> {
    private static final Logger logger = LogsCenter.getLogger(FreeCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FreeCommand
     * and returns a FreeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FreeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Invalid command format: Missing date prefix or non-empty preamble");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE_TIME);

        DateTime date = ParserUtil.parseDateTimeFree(argMultimap.getValue(PREFIX_DATE_TIME).get() + " 0000");

        assert date.toString().endsWith("0000");

        if (!date.toString().endsWith("0000")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        return new FreeCommand(date);
    }
}
