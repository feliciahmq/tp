package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.reservation.Reservation;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalReserveMate(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reservation reservationToDelete = model.getFilteredReservationList()
                .get(INDEX_FIRST_RESERVATION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESERVATION, true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
                INDEX_FIRST_RESERVATION.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getReserveMate(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReservationList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, true);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showReservationAtIndex(model, INDEX_FIRST_RESERVATION);

        Reservation reservationToDelete = model.getFilteredReservationList()
                .get(INDEX_FIRST_RESERVATION.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESERVATION, true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RESERVATION_SUCCESS,
               INDEX_FIRST_RESERVATION.getOneBased());

        Model expectedModel = new ModelManager(model.getReserveMate(), new UserPrefs());
        expectedModel.deleteReservation(reservationToDelete);
        showNoReservation(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showReservationAtIndex(model, INDEX_FIRST_RESERVATION);

        Index outOfBoundIndex = INDEX_SECOND_RESERVATION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getReserveMate().getReservationList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, true);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_RESERVATION, true);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_RESERVATION, true);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_RESERVATION, true);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different reservation -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, true);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoReservation(Model model) {
        model.updateFilteredReservationList(p -> false);

        assertTrue(model.getFilteredReservationList().isEmpty());
    }

    @Test
    public void isValidDelete_validIndex_returnsTrue() throws ParseException {
        assertTrue(DeleteCommand.isValidDelete("1")); // Valid single index
        assertTrue(DeleteCommand.isValidDelete("5")); // Another valid index
    }

    @Test
    public void isValidDelete_emptyInput_returnsFalse() throws ParseException {
        assertFalse(DeleteCommand.isValidDelete("")); // Empty string
    }

    @Test
    public void isValidDelete_validIndexWithExtraArgs_returnsTrue() throws ParseException {
        assertTrue(DeleteCommand.isValidDelete("1 confirm")); // Index followed by confirmation keyword
        assertTrue(DeleteCommand.isValidDelete("2 extra")); // Extra argument after index
    }

    @Test
    public void execute_unconfirmedDeletion_returnsConfirmationMessage() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESERVATION, false);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_CONFIRM_DELETE,
                INDEX_FIRST_RESERVATION.getOneBased());

        Model expectedModel = new ModelManager(model.getReserveMate(), new UserPrefs());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

}
