package seedu.reserve.logic.commands;

import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.Messages;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.testutil.ReservationBuilder;

public class FreeCommandTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

    @Test
    public void execute_emptyReservationList_throwsCommandException() {
        Model emptyModel = new ModelManager();
        FreeCommand freeCommand = new FreeCommand();
        assertCommandFailure(freeCommand, emptyModel, Messages.MESSAGE_EMPTY_RESERVATION_LIST);
    }

    @Test
    public void execute_hasFreeSlots_showsFreeSlots() {
        // Create reservations with gaps
        Reservation r1 = new ReservationBuilder().withDateTime(now.truncatedTo(ChronoUnit.HOURS)
                .format(FORMATTER)).build();
        Reservation r2 = new ReservationBuilder().withDateTime(now.plusHours(2).format(FORMATTER)).build();

        Model modelWithGaps = new ModelManager();
        modelWithGaps.addReservation(r1);
        modelWithGaps.addReservation(r2);

        FreeCommand freeCommand = new FreeCommand();
        String expectedMessage = "Available free time slots: \n- "
                + now.plusHours(1).truncatedTo(ChronoUnit.HOURS).format(FORMATTER) + " to "
                + now.plusHours(2).truncatedTo(ChronoUnit.HOURS).format(FORMATTER) + "\n- "
                + now.plusHours(3).truncatedTo(ChronoUnit.HOURS).format(FORMATTER) + " to "
                + now.plusDays(60).minusHours(1).truncatedTo(ChronoUnit.HOURS).format(FORMATTER);

        assertCommandSuccess(freeCommand, modelWithGaps, expectedMessage, modelWithGaps);
    }

    @Test
    public void execute_hasMultipleFreeSlots_showsAllFreeSlots() {
        Reservation r1 = new ReservationBuilder().withDateTime(now.format(FORMATTER)).build();
        Reservation r2 = new ReservationBuilder().withDateTime(now.plusHours(2).format(FORMATTER)).build();
        Reservation r3 = new ReservationBuilder().withDateTime(now.plusHours(4).format(FORMATTER)).build();

        Model modelWithMultipleGaps = new ModelManager();
        modelWithMultipleGaps.addReservation(r1);
        modelWithMultipleGaps.addReservation(r2);
        modelWithMultipleGaps.addReservation(r3);

        FreeCommand freeCommand = new FreeCommand();
        String expectedMessage = "Available free time slots: \n- "
                + now.plusHours(1).format(FORMATTER) + " to "
                + now.plusHours(2).format(FORMATTER) + "\n- "
                + now.plusHours(3).format(FORMATTER) + " to "
                + now.plusHours(4).format(FORMATTER) + "\n- "
                + now.plusHours(5).format(FORMATTER) + " to "
                + now.plusDays(60).minusHours(1).truncatedTo(ChronoUnit.HOURS).format(FORMATTER);

        assertCommandSuccess(freeCommand, modelWithMultipleGaps, expectedMessage, modelWithMultipleGaps);
    }

    @Test
    public void execute_hasFreeSlotAfterLastReservation_showsFutureSlot() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        Reservation r1 = new ReservationBuilder().withDateTime(now.format(FORMATTER)).build();

        Model modelWithFutureSlot = new ModelManager();
        modelWithFutureSlot.addReservation(r1);

        FreeCommand freeCommand = new FreeCommand();
        String expectedMessage = "Available free time slots: \n- "
                + now.plusHours(1).format(FORMATTER) + " to "
                + now.plusDays(60).minusHours(1).format(FORMATTER);

        assertCommandSuccess(freeCommand, modelWithFutureSlot, expectedMessage, modelWithFutureSlot);
    }

}
