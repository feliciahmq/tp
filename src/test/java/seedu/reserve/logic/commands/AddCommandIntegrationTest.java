package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.testutil.TypicalCustomers.getTypicalReserveMate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.reserve.logic.Messages;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.customer.Customer;
import seedu.reserve.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalReserveMate(), new UserPrefs());
    }

    @Test
    public void execute_newCustomer_success() {
        Customer validCustomer = new CustomerBuilder().build();

        Model expectedModel = new ModelManager(model.getReserveMate(), new UserPrefs());
        expectedModel.addCustomer(validCustomer);

        assertCommandSuccess(new AddCommand(validCustomer), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validCustomer)),
                expectedModel);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer customerInList = model.getReserveMate().getCustomerList().get(0);
        assertCommandFailure(new AddCommand(customerInList), model,
                AddCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

}
