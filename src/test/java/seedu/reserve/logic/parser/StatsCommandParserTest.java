package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.StatisticsCommand;

/**
 * Unit tests for {@code StatsCommandParser}.
 */
public class StatsCommandParserTest {

    private final StatsCommandParser parser = new StatsCommandParser();

    @Test
    public void parse_validArgs_returnsStatsCommand() {
        assertParseSuccess(parser, "", new StatisticsCommand());
    }

    @Test
    public void parse_whitespaceArgs_returnsStatsCommand() {
        assertParseSuccess(parser, "  ", new StatisticsCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalid_input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatisticsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nullArgs_throwsParseException() {
        assertParseFailure(parser, "123", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                StatisticsCommand.MESSAGE_USAGE));
    }
}
