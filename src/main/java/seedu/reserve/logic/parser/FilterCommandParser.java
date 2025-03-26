package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.reserve.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.reserve.logic.commands.FilterCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.reservation.DateTime;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_INVALID_ORDER = "Start date must be before end date";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_START_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        DateTime startDate = ParserUtil.parseDateTimeFilter(argMultimap.getValue(PREFIX_START_DATE).get());
        DateTime endDate = ParserUtil.parseDateTimeFilter(argMultimap.getValue(PREFIX_END_DATE).get());
        // Checks if the start date is after the end date
        if (startDate.compareTo(endDate) > 0) {
            throw new ParseException(MESSAGE_INVALID_ORDER);
        }

        assert startDate.compareTo(endDate) <= 0;
        return new FilterCommand(startDate, endDate);

    }




}
