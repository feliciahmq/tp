package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.reserve.logic.commands.StatisticsCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code StatisticsCommand} object.
 */
public class StatsCommandParser implements Parser<StatisticsCommand> {
    /**
     * Parses the given {@code String} of arguments and returns a {@code StatisticsCommand} object for execution.
     *
     * @param args The user input arguments for the stats command.
     * @return A new {@code StatisticsCommand} instance if the input is valid.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public StatisticsCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
        }
        return new StatisticsCommand();
    }

}
