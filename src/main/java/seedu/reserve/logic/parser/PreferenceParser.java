package seedu.reserve.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.commands.PreferenceCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PreferenceCommand object
 */
public class PreferenceParser implements Parser<PreferenceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PreferenceCommand
     * and returns a PreferenceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PreferenceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmedArgs.split("\\s+", 3);
        if (parts.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
        }

        String subCommand = parts[0].toLowerCase();
        try {
            Index index = ParserUtil.parseIndex(parts[1]);
            if (subCommand.equals("show")) {
                return new PreferenceCommand(index, true);
            } else if (subCommand.equals("save")) {
                if (parts.length < 3) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
                }
                String preference = parts[2];
                return new PreferenceCommand(index, preference);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PreferenceCommand.MESSAGE_USAGE), pe);
        }
    }
}
