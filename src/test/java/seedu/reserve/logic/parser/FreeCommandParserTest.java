package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DATETIME_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.FreeCommand;
import seedu.reserve.model.reservation.DateTime;

public class FreeCommandParserTest {
    private FreeCommandParser parser = new FreeCommandParser();

    @Test
    public void parse_validArgs_returnsFreeCommand() {
        // Valid date with time
        assertParseSuccess(parser, " d/2025-05-01 0000", new FreeCommand(new DateTime("2025-05-01 0000")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));

        // No date prefix
        assertParseFailure(parser, "2023-12-25 1400",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));

        // No time specified
        assertParseFailure(parser, " d/2023-12-25",
                DateTime.MESSAGE_CONSTRAINTS);

        // Invalid date format
        assertParseFailure(parser, " d/25-12-2023 1400",
                DateTime.MESSAGE_CONSTRAINTS);

        // Midnight time (0000)
        assertParseFailure(parser, " d/2023-12-25 0023",
                DateTime.MESSAGE_CONSTRAINTS);

        // Extra preamble
        assertParseFailure(parser, "extra d/2023-12-25 1400",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleDates_throwsParseException() {
        // Multiple dates
        assertParseFailure(parser, " d/" + VALID_DATETIME_AMY + " d/" + VALID_DATETIME_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
    }
}
