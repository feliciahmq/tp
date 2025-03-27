package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.logic.commands.StatisticsCommand.SHOWING_STATISTICS_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;

public class StatisticsCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_STATISTICS_MESSAGE, false, true, false);
        assertCommandSuccess(new StatisticsCommand(), model, expectedCommandResult, expectedModel);
    }
}
