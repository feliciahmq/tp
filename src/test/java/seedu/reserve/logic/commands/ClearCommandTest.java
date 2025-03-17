package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.testutil.TypicalCustomers.getTypicalReserveMate;

import org.junit.jupiter.api.Test;

import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyReserveMate_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyReserveMate_success() {
        Model model = new ModelManager(getTypicalReserveMate(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalReserveMate(), new UserPrefs());
        expectedModel.setReserveMate(new ReserveMate());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
