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
import seedu.reserve.model.reservation.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
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
        assertParseFailure(parser, userInput, MESSAGE_SHORT_NAME);
    }

    @Test
    public void parse_longArgs_throwsParseException() {
        // name length > 50
        String userInput = "sadhsfajnkfbsghdorewjkjrfkshguriewoklmsdlnfbgkjshrekwmdanfsbgrekalndfjsbghirjknjgd";
        assertParseFailure(parser, userInput, MESSAGE_LONG_NAME);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // name contains numbers or special symbols
        String userInput = "p0tAt0";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_NAME);
    }

    @Test
    public void parse_multipleShortNames_throwsParseException() {
        String userInput = "a b";
        assertParseFailure(parser, userInput, MESSAGE_SHORT_NAME);
    }

    @Test
    public void parse_mixedValidAndInvalidNames_throwsParseException() {
        String userInput = "alice 123";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_NAME);
    }

    @Test
    public void parse_nameWithSpecialCharacters_throwsParseException() {
        assertParseFailure(parser, "john!", MESSAGE_INVALID_NAME);
        assertParseFailure(parser, "al!ce", MESSAGE_INVALID_NAME);
        assertParseFailure(parser, "jo@hn", MESSAGE_INVALID_NAME);
    }

    @Test
    public void parse_emptyKeywordBetweenSpaces_throwsParseException() {
        String userInput = "john  ";
        FindCommand expected = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("john")));
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_caseInsensitivity_returnsLowercasedKeywords() {
        FindCommand expected = new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob")));
        assertParseSuccess(parser, "ALICE BoB", expected);
    }

    @Test
    public void parse_nameWithHyphenOrApostrophe_throwsParseException() {
        assertParseFailure(parser, "Anne-Marie", MESSAGE_INVALID_NAME);
        assertParseFailure(parser, "O'Connor", MESSAGE_INVALID_NAME);
    }
}
