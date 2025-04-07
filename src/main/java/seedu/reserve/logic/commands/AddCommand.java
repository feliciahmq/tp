package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DINERS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Reservation;

/**
 * Adds a reservation to the reservation book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reservation to the reservation book.\n\n"
            + "Parameters:\n"
            + "- " + PREFIX_NAME + "NAME\n"
            + "- " + PREFIX_PHONE + "PHONE\n"
            + "- " + PREFIX_EMAIL + "EMAIL\n"
            + "- " + PREFIX_NUMBER_OF_DINERS + "NUMBER OF DINERS\n"
            + "- " + PREFIX_DATE_TIME + "DATETIME\n"
            + "- " + "[" + PREFIX_OCCASION + "OCCASION]...\n\n"
            + "Example:\n" + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_NUMBER_OF_DINERS + "5 "
            + PREFIX_DATE_TIME + "2025-04-28 1800 "
            + PREFIX_OCCASION + "Birthday ";
    public static final String MESSAGE_SUCCESS = "New reservation added:\n%1$s";

    private final Reservation toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Reservation}
     */
    public AddCommand(Reservation reservation) {
        requireNonNull(reservation);
        toAdd = reservation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasReservation(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_RESERVATION);
        }

        model.addReservation(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
