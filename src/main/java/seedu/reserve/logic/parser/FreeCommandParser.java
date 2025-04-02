package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.reserve.logic.commands.FreeCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.reservation.DateTime;

/**
 * Parses input arguments and creates a new FreeCommand object
 */
public class FreeCommandParser implements Parser<FreeCommand> {

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        DateTime date = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE_TIME).get());

        if (!date.toString().endsWith("0000")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
        }

        return new FreeCommand(date);

    }




}
