package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.HelpCommand;

public class HelpCommandParserTest {
    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_emptyArgs_returnsHelpCommand() {
        // Empty string should return a general help command
        assertParseSuccess(parser, "", new HelpCommand());
    }

    @Test
    public void parse_whitespaceArgs_returnsHelpCommand() {
        // Whitespace should be trimmed and treated as empty
        assertParseSuccess(parser, "   ", new HelpCommand());
    }

    @Test
    public void parse_invalidCommandArgs_throwsParseException() {
        // Invalid command names should throw parse exception
        assertParseFailure(parser, "invalid_command",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_numericArgs_throwsParseException() {
        // Numeric arguments should throw parse exception
        assertParseFailure(parser, "123",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        // Multiple arguments should throw parse exception
        assertParseFailure(parser, "add list",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
}
