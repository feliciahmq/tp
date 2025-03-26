package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
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


}
