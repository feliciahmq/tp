package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import org.junit.jupiter.api.Test;

import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.testutil.ReservationBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code PreferenceCommand}.
 */
public class PreferenceCommandTest {

    private static final String TEST_PREFERENCE = "No nuts, allergic to seafood";
    private final Model model = new ModelManager(new ReserveMate(getTypicalReserveMate()), new UserPrefs());

    @Test
    public void execute_savePreference_success() {
        Reservation reservationInFilteredList = model.getFilteredReservationList()
            .get(INDEX_FIRST_RESERVATION.getZeroBased());

        Reservation editedReservation = new ReservationBuilder(reservationInFilteredList)
            .withPreference(TEST_PREFERENCE)
            .build();

        PreferenceCommand preferenceCommand = new PreferenceCommand(INDEX_FIRST_RESERVATION, TEST_PREFERENCE);

        String expectedMessage = String.format(PreferenceCommand.MESSAGE_SAVE_PREFERENCE_SUCCESS,
            INDEX_FIRST_RESERVATION.getOneBased());

        Model expectedModel = new ModelManager(new ReserveMate(model.getReserveMate()), new UserPrefs());
        expectedModel.setReservation(reservationInFilteredList, editedReservation);

        assertCommandSuccess(preferenceCommand, model, expectedMessage, expectedModel);
    }
}
