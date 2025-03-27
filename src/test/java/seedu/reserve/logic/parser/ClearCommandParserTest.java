package seedu.reserve.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.ClearCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

public class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_validArgs_returnsClearCommand() {
        assertParseSuccess(parser, "cfm", new ClearCommand(true));
    }

    @Test
    public void parse_emptyArgs_returnsClearCommandFalse() {
        assertParseSuccess(parser, "", new ClearCommand(false));
    }

    @Test
    public void parse_invalidArgs_returnsClearCommandFalse() {
        assertParseSuccess(parser, "invalid", new ClearCommand(false));
    }

    @Test
    public void parse_extraWords_throwsParseException() {
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse("cfm extra"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));

        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE),
                thrown.getMessage());
    }
}
