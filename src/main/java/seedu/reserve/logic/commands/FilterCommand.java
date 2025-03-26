package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.ReservationBetweenDatePredicate;

/**
 * Filters all reservations which are between a {@code startDate} and {@code endDate} provided by the user.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all reservations made between the given"
            + " date range. \n"
            + "Parameters: "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_END_DATE + "END DATE \n"
            + "Example: " + COMMAND_WORD + " sd/" + " 2026-12-31 1800 " + "ed/" + " 2026-12-31 1900";

    public static final String MESSAGE_SUCCESS = "Here are the available reservations for the date range.";
    public static final String MESSAGE_NO_RESERVATIONS = "No reservations found for the date range.";

    private final ReservationBetweenDatePredicate predicate;

    public FilterCommand(DateTime startDate, DateTime endDate) {
        this.predicate = new ReservationBetweenDatePredicate(startDate, endDate);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredReservationList(predicate);
        if (model.getFilteredReservationList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_RESERVATIONS);
        }
        assert !model.getFilteredReservationList().isEmpty();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return this.predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
