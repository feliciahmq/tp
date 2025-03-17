package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.reserve.logic.Messages;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.testutil.ReservationBuilder;

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
        Reservation validReservation = new ReservationBuilder().build();

        Model expectedModel = new ModelManager(model.getReserveMate(), new UserPrefs());
        expectedModel.addReservation(validReservation);

        assertCommandSuccess(new AddCommand(validReservation), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validReservation)),
                expectedModel);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Reservation reservationInList = model.getReserveMate().getReservationList().get(0);
        assertCommandFailure(new AddCommand(reservationInList), model,
                AddCommand.MESSAGE_DUPLICATE_RESERVATION);
    }

}
