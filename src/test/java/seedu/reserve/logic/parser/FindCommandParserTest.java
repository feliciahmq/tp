package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.reserve.logic.parser.FindCommandParser.MESSAGE_INVALID_NAME;
import static seedu.reserve.logic.parser.FindCommandParser.MESSAGE_LONG_NAME;
import static seedu.reserve.logic.parser.FindCommandParser.MESSAGE_SHORT_NAME;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.FindCommand;
import seedu.reserve.model.customer.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob")));
        assertParseSuccess(parser, "alice bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n alice \n \t bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_shortArgs_throwsParseException() {
        // name length < 2
        String userInput = "a";
        assertParseFailure(parser, userInput, String.format(MESSAGE_SHORT_NAME, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_longArgs_throwsParseException() {
        // name length > 50
        String userInput = "sadhsfajnkfbsghdorewjkjrfkshguriewoklmsdlnfbgkjshrekwmdanfsbgrekalndfjsbghirjknjgd";
        assertParseFailure(parser, userInput, String.format(MESSAGE_LONG_NAME, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // name contains numbers or special symbols
        String userInput = "p0tAt0";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_NAME, FindCommand.MESSAGE_USAGE));
    }

}
