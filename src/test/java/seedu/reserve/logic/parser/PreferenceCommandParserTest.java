package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.PreferenceCommand;

public class PreferenceCommandParserTest {

    private static final String VALID_PREFERENCE = "No nuts, allergic to seafood";
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE);

    private PreferenceParser parser = new PreferenceParser();

    @Test
    public void parse_missingParts_failure() {
        // no subcommand specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, "save", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "show", MESSAGE_INVALID_FORMAT);

        // no preference specified for save
        assertParseFailure(parser, "save 1", MESSAGE_INVALID_FORMAT);

        // no index and no preference specified
        assertParseFailure(parser, "save", MESSAGE_INVALID_FORMAT);

        // empty string
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidSubcommand_failure() {
        assertParseFailure(parser, "update 1 " + VALID_PREFERENCE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "save -5 " + VALID_PREFERENCE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "show -5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "save 0 " + VALID_PREFERENCE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "show 0", MESSAGE_INVALID_FORMAT);

        // invalid characters in index
        assertParseFailure(parser, "save abc " + VALID_PREFERENCE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "show abc", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validSaveArgs_returnsPreferenceCommand() {
        // valid save command
        PreferenceCommand expectedSaveCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, VALID_PREFERENCE);
        assertParseSuccess(parser, "save 1 " + VALID_PREFERENCE, expectedSaveCommand);

        // multiple spaces between parts
        assertParseSuccess(parser, "save    1   " + VALID_PREFERENCE, expectedSaveCommand);
    }

    @Test
    public void parse_validShowArgs_returnsPreferenceCommand() {
        // valid show command
        PreferenceCommand expectedShowCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, true);
        assertParseSuccess(parser, "show 1", expectedShowCommand);

        // multiple spaces between parts
        assertParseSuccess(parser, "show    1", expectedShowCommand);
    }
}
