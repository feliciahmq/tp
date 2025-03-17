package seedu.reserve.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalReservation.ALICE;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.reservation.exceptions.DuplicateReservationException;
import seedu.reserve.testutil.ReservationBuilder;

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
    public void resetData_withDuplicateCustomers_throwsDuplicateCustomerException() {
        // Two reservations with the same identity fields
        Reservation editedAlice = new ReservationBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        List<Reservation> newReservations = Arrays.asList(ALICE, editedAlice);
        ReserveMateStub newData = new ReserveMateStub(newReservations);

        assertThrows(DuplicateReservationException.class, () -> reserveMate.resetData(newData));
    }

    @Test
    public void hasCustomer_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reserveMate.hasReservation(null));
    }

    @Test
    public void hasCustomer_reservationNotInReserveMate_returnsFalse() {
        assertFalse(reserveMate.hasReservation(ALICE));
    }

    @Test
    public void hasCustomer_reservationInReserveMate_returnsTrue() {
        reserveMate.addReservation(ALICE);
        assertTrue(reserveMate.hasReservation(ALICE));
    }

    @Test
    public void hasCustomer_reservationWithSameIdentityFieldsInReserveMate_returnsTrue() {
        reserveMate.addReservation(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
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
