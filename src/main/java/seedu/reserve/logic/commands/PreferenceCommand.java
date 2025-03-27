package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.util.List;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Reservation;

/**
 * Saves or shows customer preferences for a reservation identified using its displayed index.
 */
public class PreferenceCommand extends Command {

    public static final String COMMAND_WORD = "pref";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves or shows customer preferences for the reservation identified by the index number.\n"
            + "Parameters for saving: " + COMMAND_WORD + " save INDEX PREFERENCE\n"
            + "Parameters for showing: " + COMMAND_WORD + " show INDEX\n"
            + "Example: " + COMMAND_WORD + " save 1 No nuts, allergic to seafood\n"
            + "Example: " + COMMAND_WORD + " show 1";

    public static final String MESSAGE_SAVE_PREFERENCE_SUCCESS = "Saved preference for reservation: %1$s";
    public static final String MESSAGE_SHOW_PREFERENCE_SUCCESS = "Preference for reservation %1$s: %2$s";
    public static final String MESSAGE_NO_PREFERENCE = "No preference has been set for this reservation.";

    private final Index index;
    private final boolean isShow;
    private final String preference;

    /**
     * Constructor for showing preference.
     */
    public PreferenceCommand(Index index, boolean isShow) {
        requireNonNull(index);
        this.index = index;
        this.isShow = isShow;
        this.preference = null;
    }

    /**
     * Constructor for saving preference.
     */
    public PreferenceCommand(Index index, String preference) {
        requireAllNonNull(index, preference);
        this.index = index;
        this.isShow = false;
        this.preference = preference;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToEdit = lastShownList.get(index.getZeroBased());
        assert reservationToEdit != null : "Reservation at the specified index cannot be null";

        if (isShow) {
            // Show preference
            String preferenceToShow = reservationToEdit.getPreference();
            if (preferenceToShow.isEmpty()) {
                return new CommandResult(MESSAGE_NO_PREFERENCE);
            }
            return new CommandResult(String.format(MESSAGE_SHOW_PREFERENCE_SUCCESS,
                    index.getOneBased(), preferenceToShow));
        } else {
            // Save preference
            Reservation editedReservation = reservationToEdit.withPreference(preference);

            model.setReservation(reservationToEdit, editedReservation);
            model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
            return new CommandResult(String.format(MESSAGE_SAVE_PREFERENCE_SUCCESS, index.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PreferenceCommand)) {
            return false;
        }

        PreferenceCommand otherCommand = (PreferenceCommand) other;
        return index.equals(otherCommand.index)
                && isShow == otherCommand.isShow
                && (preference == null ? otherCommand.preference == null
                : preference.equals(otherCommand.preference));
    }
}
