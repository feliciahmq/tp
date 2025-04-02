package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.logic.commands.AddCommand;
import seedu.reserve.logic.commands.ClearCommand;
import seedu.reserve.logic.commands.Command;
import seedu.reserve.logic.commands.DeleteCommand;
import seedu.reserve.logic.commands.EditCommand;
import seedu.reserve.logic.commands.ExitCommand;
import seedu.reserve.logic.commands.FilterCommand;
import seedu.reserve.logic.commands.FindCommand;
import seedu.reserve.logic.commands.FreeCommand;
import seedu.reserve.logic.commands.HelpCommand;
import seedu.reserve.logic.commands.ListCommand;
import seedu.reserve.logic.commands.PreferenceCommand;
import seedu.reserve.logic.commands.ShowCommand;
import seedu.reserve.logic.commands.StatisticsCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ReserveMateParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ReserveMateParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);

        case FreeCommand.COMMAND_WORD:
            return new FreeCommandParser().parse(arguments);

        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommand();

        case PreferenceCommand.COMMAND_WORD:
            return new PreferenceParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
    }

}
