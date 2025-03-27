package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.logic.commands.CommandTestUtil.showReservationAtIndex;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_SECOND_RESERVATION;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import org.junit.jupiter.api.Test;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.Messages;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.reservation.Reservation;

/**
 * Contains integration tests (interaction with the Model) for {@code PreferenceCommand}.
 */
public class PreferenceCommandTest {

    private static final String TEST_PREFERENCE = "No nuts, allergic to seafood";
    private static final String DIFFERENT_PREFERENCE = "Window seat preferred";

    private final Model model = new ModelManager(new ReserveMate(getTypicalReserveMate()), new UserPrefs());

    @Test
    public void execute_savePreference_success() {
        Reservation reservationInFilteredList = model.getFilteredReservationList()
            .get(INDEX_FIRST_RESERVATION.getZeroBased());
        Reservation editedReservation = reservationInFilteredList.withPreference(TEST_PREFERENCE);
        PreferenceCommand preferenceCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, TEST_PREFERENCE);

        String expectedMessage = String.format(PreferenceCommand.MESSAGE_SAVE_PREFERENCE_SUCCESS,
                INDEX_FIRST_RESERVATION.getOneBased());

        Model expectedModel = new ModelManager(new ReserveMate(model.getReserveMate()), new UserPrefs());
        expectedModel.setReservation(reservationInFilteredList, editedReservation);

        assertCommandSuccess(preferenceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_showPreference_success() {
        // First save a preference
        Reservation reservationInFilteredList = model.getFilteredReservationList()
            .get(INDEX_FIRST_RESERVATION.getZeroBased());
        Reservation editedReservation = reservationInFilteredList.withPreference(TEST_PREFERENCE);
        model.setReservation(reservationInFilteredList, editedReservation);
        // Then show the preference
        PreferenceCommand preferenceCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, true);

        String expectedMessage = String.format(PreferenceCommand.MESSAGE_SHOW_PREFERENCE_SUCCESS,
                INDEX_FIRST_RESERVATION.getOneBased(), TEST_PREFERENCE);

        assertCommandSuccess(preferenceCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_showEmptyPreference_returnsNoPreferenceMessage() {
        // Ensure the reservation has no preference set (or has default "None")
        Reservation reservationInFilteredList = model.getFilteredReservationList()
            .get(INDEX_FIRST_RESERVATION.getZeroBased());
        if (!reservationInFilteredList.getPreference().isEmpty()) {
            Reservation editedReservation = reservationInFilteredList.withPreference("");
            model.setReservation(reservationInFilteredList, editedReservation);
        }
        PreferenceCommand preferenceCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, true);
        assertCommandSuccess(preferenceCommand, model, PreferenceCommand.MESSAGE_NO_PREFERENCE, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        PreferenceCommand saveCommand = new PreferenceCommand(outOfBoundIndex, TEST_PREFERENCE);
        PreferenceCommand showCommand = new PreferenceCommand(outOfBoundIndex, true);

        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        assertCommandFailure(showCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showReservationAtIndex(model, INDEX_FIRST_RESERVATION);
        Index outOfBoundIndex = INDEX_SECOND_RESERVATION;
        // ensures that outOfBoundIndex is still in bounds of reservation book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getReserveMate().getReservationList().size());

        PreferenceCommand saveCommand = new PreferenceCommand(outOfBoundIndex, TEST_PREFERENCE);
        PreferenceCommand showCommand = new PreferenceCommand(outOfBoundIndex, true);

        assertCommandFailure(saveCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        assertCommandFailure(showCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final PreferenceCommand standardSaveCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, TEST_PREFERENCE);
        final PreferenceCommand standardShowCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, true);

        // same object -> returns true
        assertTrue(standardSaveCommand.equals(standardSaveCommand));
        assertTrue(standardShowCommand.equals(standardShowCommand));

        // same values -> returns true
        PreferenceCommand saveCommandCopy = new PreferenceCommand(INDEX_FIRST_RESERVATION, TEST_PREFERENCE);
        PreferenceCommand showCommandCopy = new PreferenceCommand(INDEX_FIRST_RESERVATION, true);
        assertTrue(standardSaveCommand.equals(saveCommandCopy));
        assertTrue(standardShowCommand.equals(showCommandCopy));

        // different index -> returns false
        assertFalse(standardSaveCommand.equals(new PreferenceCommand(INDEX_SECOND_RESERVATION, TEST_PREFERENCE)));
        assertFalse(standardShowCommand.equals(new PreferenceCommand(INDEX_SECOND_RESERVATION, true)));

        // different preference -> returns false
        assertFalse(standardSaveCommand.equals(new PreferenceCommand(INDEX_FIRST_RESERVATION, DIFFERENT_PREFERENCE)));

        // save vs show -> returns false
        assertFalse(standardSaveCommand.equals(standardShowCommand));
    }
}
