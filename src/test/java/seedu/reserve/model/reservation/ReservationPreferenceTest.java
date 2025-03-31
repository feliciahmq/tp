package seedu.reserve.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalReservation.ALICE;

import org.junit.jupiter.api.Test;

import seedu.reserve.testutil.ReservationBuilder;

public class ReservationPreferenceTest {

    private static final String TEST_PREFERENCE = "No nuts, allergic to seafood";
    private static final String DIFFERENT_PREFERENCE = "Window seat preferred";

    @Test
    public void getPreference_defaultConstructor_returnsNone() {
        assertEquals("None", new ReservationBuilder().build().getPreference().toString());
    }

    @Test
    public void getPreference_customPreference_returnsCorrectPreference() {
        assertEquals(TEST_PREFERENCE, new ReservationBuilder()
            .withPreference(TEST_PREFERENCE).build().getPreference().toString());
    }

    @Test
    public void withPreference_nullPreference_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReservationBuilder()
            .withPreference(null).build());
    }

    @Test
    public void withPreference_validPreference_returnsReservationWithUpdatedPreference() {
        Reservation originalReservation = new ReservationBuilder().build();
        Reservation updatedReservation = new ReservationBuilder(originalReservation)
            .withPreference(TEST_PREFERENCE)
            .build();
        // Check that the preference was updated
        assertEquals(TEST_PREFERENCE, updatedReservation.getPreference().toString());
        // Check that other fields remain the same
        assertEquals(originalReservation.getName(), updatedReservation.getName());
        assertEquals(originalReservation.getPhone(), updatedReservation.getPhone());
        assertEquals(originalReservation.getEmail(), updatedReservation.getEmail());
        assertEquals(originalReservation.getDiners(), updatedReservation.getDiners());
        assertEquals(originalReservation.getDateTime(), updatedReservation.getDateTime());
        assertEquals(originalReservation.getOccasions(), updatedReservation.getOccasions());
    }

    @Test
    public void equals_samePreference_returnsTrue() {
        Reservation reservation1 = new ReservationBuilder(ALICE).withPreference(TEST_PREFERENCE).build();
        Reservation reservation2 = new ReservationBuilder(ALICE).withPreference(TEST_PREFERENCE).build();
        assertTrue(reservation1.equals(reservation2));
    }

    @Test
    public void equals_differentPreference_returnsFalse() {
        Reservation reservation1 = new ReservationBuilder(ALICE).withPreference(TEST_PREFERENCE).build();
        Reservation reservation2 = new ReservationBuilder(ALICE).withPreference(DIFFERENT_PREFERENCE).build();
        assertFalse(reservation1.equals(reservation2));
    }

    @Test
    public void hashCode_samePreference_sameHashCode() {
        Reservation reservation1 = new ReservationBuilder(ALICE).withPreference(TEST_PREFERENCE).build();
        Reservation reservation2 = new ReservationBuilder(ALICE).withPreference(TEST_PREFERENCE).build();
        assertEquals(reservation1.hashCode(), reservation2.hashCode());
    }

    @Test
    public void toString_containsPreference() {
        Reservation reservation = new ReservationBuilder().withPreference(TEST_PREFERENCE).build();
        String toStringResult = reservation.toString();
        assertTrue(toStringResult.contains("preference=" + TEST_PREFERENCE));
    }
}
