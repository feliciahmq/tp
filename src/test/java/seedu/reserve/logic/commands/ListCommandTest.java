package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.logic.commands.CommandTestUtil.showReservationAtIndex;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.reserve.logic.Messages;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalReserveMate(), new UserPrefs());
        expectedModel = new ModelManager(model.getReserveMate(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showReservationAtIndex(model, INDEX_FIRST_RESERVATION);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        // Create an empty model
        Model emptyModel = new ModelManager();

        // Check for CommandException
        assertCommandFailure(new ListCommand(), emptyModel, Messages.MESSAGE_EMPTY_RESERVATION_LIST);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        ListCommand listCommand = new ListCommand();
        assert(listCommand.equals(listCommand));
    }


    @Test
    public void equals_differentInstances_returnsTrue() {
        ListCommand listCommand1 = new ListCommand();
        ListCommand listCommand2 = new ListCommand();
        assert(listCommand1.equals(listCommand2));
    }


    @Test
    public void equals_differentObject_returnsFalse() {
        ListCommand listCommand = new ListCommand();
        Object other = new Object();
        assert(!listCommand.equals(other));
    }

    @Test
    public void isValidListCommand_validInputs_returnsTrue() {
        // Empty input (valid case)
        assertTrue(ListCommand.isValidListCommand(""));

        // Input with spaces only (still valid because it trims)
        assertTrue(ListCommand.isValidListCommand("   "));
    }

    @Test
    public void isValidListCommand_invalidInputs_returnsFalse() {
        // Non-empty text input
        assertFalse(ListCommand.isValidListCommand("abc"));

        // Random special characters
        assertFalse(ListCommand.isValidListCommand("@!#"));

        // Combination of spaces and text
        assertFalse(ListCommand.isValidListCommand(" list "));

        // Numeric values
        assertFalse(ListCommand.isValidListCommand("123"));
    }
}
