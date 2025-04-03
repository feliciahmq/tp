package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.logic.commands.StatisticsCommand.SHOWING_STATISTICS_MESSAGE;
import static seedu.reserve.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.testutil.TypicalReservation;

public class StatisticsCommandTest {
    @Test
    public void execute_noReservations_throwsCommandException() {
        Model emptyModel = new ModelManager(); // empty model
        StatisticsCommand statsCommand = new StatisticsCommand();

        assertThrows(CommandException.class, () -> statsCommand.execute(emptyModel));
    }

    @Test
    public void execute_withReservations_success() {
        Model model = new ModelManager(TypicalReservation.getTypicalReserveMate(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalReservation.getTypicalReserveMate(), new UserPrefs());

        CommandResult expectedCommandResult = new CommandResult(SHOWING_STATISTICS_MESSAGE, false, true, false);
        assertCommandSuccess(new StatisticsCommand(), model, expectedCommandResult, expectedModel);
    }
}
