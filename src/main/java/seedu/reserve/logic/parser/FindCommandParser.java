package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.reserve.logic.commands.FindCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.reservation.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_INVALID_NAME = "Invalid name format. Name should contain only letters.";
    public static final String MESSAGE_SHORT_NAME = "Invalid name. The name must have at least 2 characters.";
    public static final String MESSAGE_LONG_NAME = "Invalid name. The name must be 50 characters or less.";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        List<String> validKeywords = new ArrayList<>();

        for (String keyword : nameKeywords) {
            String trimmedKeyword = keyword.trim();

            if (!trimmedKeyword.matches("[A-Za-z]+")) {
                throw new ParseException(MESSAGE_INVALID_NAME);
            } else if (trimmedKeyword.length() < 2) {
                throw new ParseException(MESSAGE_SHORT_NAME);
            } else if (trimmedKeyword.length() > 50) {
                throw new ParseException(MESSAGE_LONG_NAME);
            }

            validKeywords.add(trimmedKeyword.toLowerCase());
        }

        if (validKeywords.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_NAME);
        }

        return new FindCommand(new NameContainsKeywordsPredicate(validKeywords));
    }

}
