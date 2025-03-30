package seedu.reserve.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_BIRTHDAY;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalReservation.ALICE;
import static seedu.reserve.testutil.TypicalReservation.BENSON;
import static seedu.reserve.testutil.TypicalReservation.ELLE;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.reservation.exceptions.DuplicateReservationException;
import seedu.reserve.testutil.ReservationBuilder;
import seedu.reserve.testutil.ReserveMateBuilder;

public class ReserveMateTest {

    private final ReserveMate reserveMate = new ReserveMate();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), reserveMate.getReservationList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reserveMate.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyReserveMate_replacesData() {
        ReserveMate newData = getTypicalReserveMate();
        reserveMate.resetData(newData);
        assertEquals(newData, reserveMate);
    }

    @Test
    public void resetData_withDuplicateReservations_throwsDuplicateReservationException() {
        // Two reservations with the same identity fields
        Reservation editedAlice = new ReservationBuilder(ALICE)
                .withOccasions(VALID_OCCASION_BIRTHDAY).build();
        List<Reservation> newReservations = Arrays.asList(ALICE, editedAlice);
        ReserveMateStub newData = new ReserveMateStub(newReservations);

        assertThrows(DuplicateReservationException.class, () -> reserveMate.resetData(newData));
    }

    @Test
    public void hasReservation_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reserveMate.hasReservation(null));
    }

    @Test
    public void hasReservation_reservationNotInReserveMate_returnsFalse() {
        assertFalse(reserveMate.hasReservation(ALICE));
    }

    @Test
    public void hasReservation_reservationInReserveMate_returnsTrue() {
        reserveMate.addReservation(ALICE);
        assertTrue(reserveMate.hasReservation(ALICE));
    }

    @Test
    public void hasReservation_reservationWithSameIdentityFieldsInReserveMate_returnsTrue() {
        reserveMate.addReservation(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE)
                .withOccasions(VALID_OCCASION_BIRTHDAY).build();
        assertTrue(reserveMate.hasReservation(editedAlice));
    }

    @Test
    public void getReservationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> reserveMate.getReservationList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ReserveMate.class.getCanonicalName() + "{reservations="
                + reserveMate.getReservationList() + "}";
        assertEquals(expected, reserveMate.toString());
    }

    @Test
    public void getSumOfReservationsPerDiners_reservationsHaveZeroDinerGroup_success() {
        ReserveMate reserveMate = new ReserveMateBuilder().build();
        HashMap<String, Integer> expectedStatistics = new HashMap<>();
        HashMap<String, Integer> actualStatistics = reserveMate.getSumOfReservationsPerDiner();

        assertEquals(expectedStatistics, actualStatistics);
    }

    @Test
    public void getSumOfReservationsPerDiners_reservationsHaveOneDinerGroup_success() {
        ReserveMate reserveMate = new ReserveMateBuilder()
                .withReservation(BENSON)
                .build();
        HashMap<String, Integer> expectedStatistics = new HashMap<>();
        expectedStatistics.put("3", 1);
        HashMap<String, Integer> actualStatistics = reserveMate.getSumOfReservationsPerDiner();

        assertEquals(expectedStatistics, actualStatistics);
    }

    @Test
    public void getSumOfReservationsPerDiners_reservationsHaveOneDinerGroupTwoReservations_success() {
        Reservation anotherReservation = new ReservationBuilder().withName("Benson Meier")
                .withEmail("basil@example.com")
                .withPhone("93748263")
                .withDiners("3")
                .withDateTime("2025-04-24 1200")
                .withOccasions("friends")
                .build();
        ReserveMate reserveMate = new ReserveMateBuilder()
                .withReservation(BENSON)
                .withReservation(anotherReservation)
                .build();
        HashMap<String, Integer> expectedStatistics = new HashMap<>();
        expectedStatistics.put("3", 1);
        expectedStatistics.put("3", 2);
        HashMap<String, Integer> actualStatistics = reserveMate.getSumOfReservationsPerDiner();

        assertEquals(expectedStatistics, actualStatistics);
    }

    @Test
    public void getSumOfReservationsPerDiners_reservationsHaveMoreThanOneDinerGroup_success() {
        ReserveMate reserveMate = new ReserveMateBuilder()
                .withReservation(BENSON)
                .withReservation(ELLE)
                .build();
        HashMap<String, Integer> expectedStatistics = new HashMap<>();
        expectedStatistics.put("3", 1);
        expectedStatistics.put("1", 1);
        HashMap<String, Integer> actualStatistics = reserveMate.getSumOfReservationsPerDiner();

        assertEquals(expectedStatistics, actualStatistics);
    }

    /**
     * A stub ReadOnlyReserveMate whose reservations list can violate interface constraints.
     */
    private static class ReserveMateStub implements ReadOnlyReserveMate {
        private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        ReserveMateStub(Collection<Reservation> reservations) {
            this.reservations.setAll(reservations);
        }

        @Override
        public ObservableList<Reservation> getReservationList() {
            return reservations;
        }
    }

}
