package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.testutil.TypicalReservation.CARL;
import static seedu.reserve.testutil.TypicalReservation.FIONA;
import static seedu.reserve.testutil.TypicalReservation.GEORGE;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.ReservationBetweenDatePredicate;

public class FilterCommandTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static DateTime endDate = DateTime.fromFileString(LocalDateTime.now().plusDays(1)
            .truncatedTo(ChronoUnit.HOURS).format(FORMATTER).toString());
    private static DateTime startDate = DateTime.fromFileString(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS)
            .format(FORMATTER).toString());
    private static DateTime startDate2 = DateTime.fromFileString(LocalDateTime.now()
            .plusHours(1).truncatedTo(ChronoUnit.HOURS).format(FORMATTER).toString());

    private Model model = new ModelManager(getTypicalReserveMate(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalReserveMate(), new UserPrefs());

    @Test
    public void equals() {
        FilterCommand filterCommand = new FilterCommand(startDate, endDate);
        FilterCommand filterCommand2 = new FilterCommand(startDate, endDate);
        FilterCommand filterCommand3 = new FilterCommand(startDate2, endDate);

        // check same object
        assertTrue(filterCommand.equals(filterCommand));

        // check same start and end dates
        assertTrue(filterCommand.equals(filterCommand2));

        // check different start and end dates
        assertFalse(filterCommand.equals(filterCommand3));

        // different types -> returns false
        assertFalse(filterCommand.equals(1));

        // null -> returns false
        assertFalse(filterCommand.equals(null));
    }

    @Test
    public void filterCommand_noReservationsFound() {
        DateTime startDate = DateTime.fromFileString("2015-05-13 1400");
        DateTime endDate = DateTime.fromFileString("2015-05-13 1500");
        // target result
        ReservationBetweenDatePredicate predicate = new ReservationBetweenDatePredicate(startDate, endDate);
        expectedModel.updateFilteredReservationList(predicate);
        String expectedMessage = FilterCommand.MESSAGE_NO_RESERVATIONS;
        // model
        FilterCommand filterCommand = new FilterCommand(startDate, endDate);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredReservationList());
    }

    @Test
    public void filterCommand_someReservationsFound() {
        DateTime startDate = DateTime.fromFileString("2025-04-12 1000");
        DateTime endDate = DateTime.fromFileString("2025-04-18 1400");

        String expectedMessage = FilterCommand.MESSAGE_SUCCESS;
        ReservationBetweenDatePredicate predicate = new ReservationBetweenDatePredicate(startDate, endDate);
        expectedModel.updateFilteredReservationList(predicate);

        FilterCommand filterCommand = new FilterCommand(startDate, endDate);
        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, CARL, GEORGE), expectedModel.getFilteredReservationList());

    }

    @Test
    public void toStringTest() {
        ReservationBetweenDatePredicate predicate = new ReservationBetweenDatePredicate(startDate, endDate);
        FilterCommand filterCommand = new FilterCommand(startDate, endDate);
        String expected = FilterCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }
}
