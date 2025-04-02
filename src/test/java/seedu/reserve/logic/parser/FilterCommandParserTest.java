package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.FilterCommand;
import seedu.reserve.model.reservation.DateTime;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void filterParse_noArguments() throws Exception {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void filterParse_invalidNumberOfArguments() throws Exception {
        assertParseFailure(parser, " sd/ 2026-12-01 1300", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ed/ 2026-12-01 1400", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void filterParse_invalidCliSyntax() throws Exception {
        assertParseFailure(parser, " sdm/ 2026-12-01 1300 eds/ 2026-12-04 1600",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

    }

    @Test
    public void filterParse_startDateBeforeEndDate() throws Exception {
        assertParseFailure(parser, " sd/ 2026-12-15 1500 ed/ 2025-12-12 1400",
                FilterCommandParser.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void filterParse_invalidDates() throws Exception {
        assertParseFailure(parser, " sd/ 2026-12-15 1530 ed/ 2025-12-12 1400",
                DateTime.MESSAGE_CONSTRAINTS_FILTER);
        assertParseFailure(parser, " sd/ 2026-12-15 130 ed/ 2025-12-12 1400",
                DateTime.MESSAGE_CONSTRAINTS_FILTER);
    }

    @Test
    public void filterParse_success() throws Exception {
        DateTime startDate = DateTime.fromFileString("2026-12-01 1300");
        DateTime endDate = DateTime.fromFileString("2026-12-01 1400");
        FilterCommand expectedFilter = new FilterCommand(startDate, endDate);
        assertParseSuccess(parser, " sd/ 2026-12-01 1300 ed/ 2026-12-01 1400", expectedFilter);
    }
}
