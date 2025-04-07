package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.FreeCommand;
import seedu.reserve.model.reservation.DateTime;

public class FreeCommandParserTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private FreeCommandParser parser = new FreeCommandParser();

    @Test
    public void parse_validArgs_returnsFreeCommand() {
        // Valid date with time
        assertParseSuccess(parser, " d/2025-05-01", new FreeCommand(new DateTime("2025-05-01 0000")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));

        // No date prefix
        assertParseFailure(parser, "2023-12-25",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));

        // No time specified
        assertParseFailure(parser, " d/2023-12-25",
                DateTime.MESSAGE_CONSTRAINTS_FREE);

        // Invalid date format
        assertParseFailure(parser, " d/25-12-2023",
                DateTime.MESSAGE_CONSTRAINTS_FREE);

        // Midnight time (0000)
        assertParseFailure(parser, " d/2023-12-25",
                DateTime.MESSAGE_CONSTRAINTS_FREE);

        // Extra preamble
        assertParseFailure(parser, "extra d/2023-12-25",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        // Various whitespace patterns
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " d/    ",
                DateTime.MESSAGE_CONSTRAINTS_FREE);
    }

    @Test
    public void parse_withExplicitTime_throwsParseException() {
        // Should reject if time is explicitly specified
        assertParseFailure(parser, " d/2025-05-01 1200",
                DateTime.MESSAGE_CONSTRAINTS_FREE);

        assertParseFailure(parser, " d/2025-05-01 0000",
                DateTime.MESSAGE_CONSTRAINTS_FREE);
    }

    @Test
    public void parse_caseInsensitivePrefix_validCommand() {
        // Should work with different prefix cases
        assertParseSuccess(parser, " D/2025-05-01",
                new FreeCommand(new DateTime("2025-05-01 0000")));
    }

    @Test
    public void parse_dateBeyond60Days_throwsParseException() {
        LocalDateTime today = LocalDate.now().atStartOfDay();

        String invalidDateStr = today.plusDays(61).format(FORMATTER);
        String boundaryDateStr = today.plusDays(59).format(FORMATTER);

        System.out.println(invalidDateStr);
        System.out.println(boundaryDateStr + " 0000");

        // Test invalid case (61 days)
        assertParseFailure(parser, " d/" + invalidDateStr,
                DateTime.MESSAGE_CONSTRAINTS_FREE);

        // Test boundary case (60 days)
        assertParseSuccess(parser, " d/" + boundaryDateStr,
                new FreeCommand(new DateTime(boundaryDateStr + " 0000")));
    }

    @Test
    public void parse_multipleDates_throwsParseException() {
        // Multiple dates
        assertParseFailure(parser, " d/2025-04-28 d/2025-05-01",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE_TIME));
    }
}
