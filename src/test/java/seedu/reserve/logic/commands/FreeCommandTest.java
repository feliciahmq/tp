package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.testutil.ReservationBuilder;

public class FreeCommandTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTime TEST_DATE = new DateTime("2025-05-01 0000");
    private Model model = new ModelManager(getTypicalReserveMate(), new UserPrefs());

    @Test
    public void execute_noReservationsOnDate_allDayFree() {
        FreeCommand freeCommand = new FreeCommand(TEST_DATE);
        String expectedMessage = FreeCommand.MESSAGE_ALL_FREE_SLOTS;
        assertCommandSuccess(freeCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_hasFreeSlots_showsFreeSlots() {
        // Add reservations with gaps
        Reservation r1 = new ReservationBuilder()
                .withDateTime(TEST_DATE.value.plusHours(10).format(FORMATTER))
                .build();
        Reservation r2 = new ReservationBuilder()
                .withDateTime(TEST_DATE.value.plusHours(14).format(FORMATTER))
                .build();
        model.addReservation(r1);
        model.addReservation(r2);

        FreeCommand freeCommand = new FreeCommand(new DateTime(TEST_DATE.toString()));
        String expectedMessage = "Available free time slots:"
                + "\n- 2025-05-01 0000 to 2025-05-01 1000"
                + "\n- 2025-05-01 1100 to 2025-05-01 1400"
                + "\n- 2025-05-01 1500 to 2025-05-02 0000";

        assertCommandSuccess(freeCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_noFreeSlots_showsNoSlotsMessage() {
        // Add reservations covering the entire day
        for (int i = 0; i < 24; i++) {
            Reservation r = new ReservationBuilder()
                    .withDateTime(TEST_DATE.value.plusHours(i).format(FORMATTER))
                    .build();
            model.addReservation(r);
        }

        FreeCommand freeCommand = new FreeCommand(new DateTime(TEST_DATE.toString()));
        String expectedMessage = FreeCommand.MESSAGE_NO_FREE_SLOTS;
        assertCommandSuccess(freeCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_freeSlotBeforeFirstReservation_showsEarlySlot() {
        Reservation r1 = new ReservationBuilder()
                .withDateTime(TEST_DATE.value.plusHours(12).format(FORMATTER))
                .build();
        model.addReservation(r1);

        FreeCommand freeCommand = new FreeCommand(new DateTime(TEST_DATE.toString()));
        String expectedMessage = "Available free time slots:"
                + "\n- 2025-05-01 0000 to 2025-05-01 1200"
                + "\n- 2025-05-01 1300 to 2025-05-02 0000";

        assertCommandSuccess(freeCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_freeSlotAfterLastReservation_showsLateSlot() {
        Reservation r1 = new ReservationBuilder()
                .withDateTime(TEST_DATE.value.plusHours(8).format(FORMATTER))
                .build();
        model.addReservation(r1);

        FreeCommand freeCommand = new FreeCommand(new DateTime(TEST_DATE.toString()));
        String expectedMessage = "Available free time slots:"
                + "\n- 2025-05-01 0000 to 2025-05-01 0800"
                + "\n- 2025-05-01 0900 to 2025-05-02 0000";

        assertCommandSuccess(freeCommand, model, expectedMessage, model);
    }
}
