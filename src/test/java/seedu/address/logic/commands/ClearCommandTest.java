package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalReserveMate;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReserveMate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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
