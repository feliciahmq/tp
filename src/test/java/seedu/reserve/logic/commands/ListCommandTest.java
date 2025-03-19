package seedu.reserve.logic.commands;

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
}
