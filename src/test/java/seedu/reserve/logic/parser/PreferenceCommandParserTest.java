package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.PreferenceCommand;

public class PreferenceCommandParserTest {

    public static final String MESSAGE_NEGATIVE_INDEX =
        "The reservation index must be a non-negative integer greater than 0!";
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE);
    private static final String VALID_PREFERENCE = "No nuts and allergic to seafood";

    private final PreferenceParser parser = new PreferenceParser();

    @Test
    public void parse_missingParts_failure() {
        // no subcommand specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, "save", MESSAGE_INVALID_FORMAT);

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
        assertParseFailure(parser, "save -5 " + VALID_PREFERENCE, MESSAGE_NEGATIVE_INDEX);

        // zero index
        assertParseFailure(parser, "save 0 " + VALID_PREFERENCE, MESSAGE_NEGATIVE_INDEX);

        // invalid characters in index
        assertParseFailure(parser, "save abc " + VALID_PREFERENCE, MESSAGE_NEGATIVE_INDEX);
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
    public void parse_preferenceTextTooLong_throwsParseException() {
        // Generate a preference text that exceeds the maximum length (50 characters)
        StringBuilder longPreference = new StringBuilder();
        for (int i = 0; i < 60; i++) { // Using 60 characters (exceeds the 50 limit)
            longPreference.append('a');
        }
        String userInput = "save 1 " + longPreference;
        assertParseFailure(parser, userInput, PreferenceParser.MESSAGE_PREFERENCE_TOO_LONG);
    }

    @Test
    public void parse_invalidPreferenceCharacters_failure() {
        // Test with special characters
        assertParseFailure(parser, "save 1 Invalid!@#", PreferenceParser.MESSAGE_INVALID_PREFERENCE_CHARACTERS);

        // Test with underscore
        assertParseFailure(parser, "save 1 Invalid_Preference", PreferenceParser.MESSAGE_INVALID_PREFERENCE_CHARACTERS);

        // Test with other symbols
        assertParseFailure(parser, "save 1 Preference$", PreferenceParser.MESSAGE_INVALID_PREFERENCE_CHARACTERS);
        assertParseFailure(parser, "save 1 Preference&Text", PreferenceParser.MESSAGE_INVALID_PREFERENCE_CHARACTERS);
    }
}
