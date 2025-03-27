package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyReserveMate_throwsCommandException() {
        // Set up a model with an empty ReserveMate
        Model model = new ModelManager();

        // Check for CommandException failure with the expected message
        assertCommandFailure(new ClearCommand(true), model, ClearCommand.MESSAGE_NO_RESERVATIONS_TO_CLEAR);
    }

    @Test
    public void execute_nonEmptyReserveMate_success() {
        Model model = new ModelManager(getTypicalReserveMate(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalReserveMate(), new UserPrefs());
        expectedModel.setReserveMate(new ReserveMate());

        assertCommandSuccess(new ClearCommand(true), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void isValidConfirm_validConfirmation_returnsTrue() throws ParseException {
        // Single valid confirmation "cfm"
        assertTrue(ClearCommand.isValidConfirm("cfm"));

        // Single valid confirmation with extra spaces
        assertTrue(ClearCommand.isValidConfirm("cfm  "));

        // Any other word than cfm is still valid but will set isConfimed = false
        assertTrue(ClearCommand.isValidConfirm("false"));
    }

    @Test
    public void isValidConfirm_invalidConfirmation_returnsFalse() throws ParseException {
        // Invalid confirmation with extra words should be invalid
        assertFalse(ClearCommand.isValidConfirm("cfm extra"));

        // Any other invalid confirmation like "confirm" should also return false
        assertFalse(ClearCommand.isValidConfirm("cfm   hello"));
    }

    @Test
    public void execute_confirmedWithNoReservations_throwsCommandException() {
        Model model = new ModelManager();
        assertCommandFailure(new ClearCommand(true), model, ClearCommand.MESSAGE_NO_RESERVATIONS_TO_CLEAR);
    }

    @Test
    public void execute_notConfirmed_throwsCommandException() {
        Model model = new ModelManager(getTypicalReserveMate(), new UserPrefs());

        // Check for CommandException when not confirmed
        assertCommandFailure(new ClearCommand(false), model, ClearCommand.MESSAGE_ADD_CONFIRM_CLEAR_RESERVATION);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ClearCommand command = new ClearCommand(true);
        assertEquals(command, command);
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        ClearCommand command1 = new ClearCommand(true);
        ClearCommand command2 = new ClearCommand(false);
        assertNotEquals(command1, command2);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ClearCommand command1 = new ClearCommand(true);
        ClearCommand command2 = new ClearCommand(true);
        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ClearCommand command = new ClearCommand(true);
        assertNotEquals(5, command); // Different type
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        ClearCommand command1 = new ClearCommand(true);
        ClearCommand command2 = new ClearCommand(true);
        assertEquals(command1.hashCode(), command2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        ClearCommand command1 = new ClearCommand(true);
        ClearCommand command2 = new ClearCommand(false);
        assertNotEquals(command1.hashCode(), command2.hashCode());
    }


}
