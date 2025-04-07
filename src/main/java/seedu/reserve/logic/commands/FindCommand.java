package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.Messages.MESSAGE_NO_RESERVATIONS;

import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.logic.Messages;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.NameContainsKeywordsPredicate;

/**
 * Finds and lists all reservations in reservation book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all reservations whose names contain any of "
            + "the specified keywords (case-insensitive).\n\n"
            + "Parameters: NAME [MORE_NAMES]...\n\n"
            + "Example: " + COMMAND_WORD + " alice Bob Charlie";


    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredReservationList(predicate);
        if (model.getFilteredReservationList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_RESERVATIONS);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_RESERVATIONS_LISTED_OVERVIEW,
                        model.getFilteredReservationList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
