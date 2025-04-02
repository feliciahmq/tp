package seedu.reserve.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.ParserUtil.parsePrefIndex;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.commands.PreferenceCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PreferenceCommand object
 */
public class PreferenceParser implements Parser<PreferenceCommand> {

    public static final String MESSAGE_INVALID_PREFERENCE_CHARACTERS =
        "Preferences should only contain spaces and alphanumeric characters.";
    private static final int MAX_PREFERENCE_LENGTH = 50;
    public static final String MESSAGE_PREFERENCE_TOO_LONG =
        "Preference text cannot exceed " + MAX_PREFERENCE_LENGTH + " characters.";
    /**
     * Parses the given {@code String} of arguments in the context of the PreferenceCommand
     * and returns a PreferenceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PreferenceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        validateArgsNotEmpty(trimmedArgs);

        String[] parts = trimmedArgs.split("\\s+", 3);
        validateArgsLength(parts);

        String subCommand = validateAndExtractSubCommand(parts[0]);
        Index index = parsePrefIndex(parts[1]);

        return createCommand(subCommand, index, parts);
    }

    /**
     * Validates that the arguments string is not empty.
     * @throws ParseException if the arguments string is empty
     */
    private void validateArgsNotEmpty(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validates that the parts array has at least the required number of elements.
     * @throws ParseException if the parts array is too short
     */
    private void validateArgsLength(String[] parts) throws ParseException {
        if (parts.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Creates the appropriate PreferenceCommand based on the subcommand.
     * @throws ParseException if the subcommand is invalid or required arguments are missing
     */
    private PreferenceCommand createCommand(String subCommand, Index index, String[] parts) throws ParseException {
        switch (subCommand) {
        case "save":
            return createSaveCommand(index, parts);
        default:
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Creates a save preference command.
     * @throws ParseException if the preference text is missing or too long
     */
    private PreferenceCommand createSaveCommand(Index index, String[] parts) throws ParseException {
        if (parts.length < 3) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
        }

        String preference = parts[2];
        validatePreferenceLength(preference);
        validatePreferenceContent(preference);

        return new PreferenceCommand(index, preference);
    }

    /**
     * Validates that the preference text is not too long.
     * @throws ParseException if the preference text exceeds the maximum length
     */
    private void validatePreferenceLength(String preference) throws ParseException {
        if (preference.length() > MAX_PREFERENCE_LENGTH) {
            throw new ParseException(MESSAGE_PREFERENCE_TOO_LONG);
        }
    }

    /**
     * Validates that the preference text contains only alphanumeric characters.
     * @throws ParseException if the preference contains invalid characters
     */
    private void validatePreferenceContent(String preference) throws ParseException {
        if (!preference.matches("[a-zA-Z0-9 ]+")) {
            throw new ParseException(MESSAGE_INVALID_PREFERENCE_CHARACTERS);
        }
    }

    /**
     * Validates the subcommand and returns it in lowercase.
     * @throws ParseException if the subcommand is not valid
     */
    private String validateAndExtractSubCommand(String subCommand) throws ParseException {
        String lowerCaseSubCommand = subCommand.toLowerCase();
        if (!lowerCaseSubCommand.equals("save")) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
        }
        return lowerCaseSubCommand;
    }
}
