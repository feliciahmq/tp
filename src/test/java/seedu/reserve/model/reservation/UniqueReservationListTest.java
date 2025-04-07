package seedu.reserve.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_BIRTHDAY;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalReservation.ALICE;
import static seedu.reserve.testutil.TypicalReservation.BOB;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.reserve.model.reservation.exceptions.DuplicateReservationException;
import seedu.reserve.model.reservation.exceptions.ReservationNotFoundException;
import seedu.reserve.testutil.ReservationBuilder;

public class UniqueReservationListTest {

    private final UniqueReservationList uniqueReservationList = new UniqueReservationList();

    @Test
    public void contains_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.contains(null));
    }

    @Test
    public void contains_reservationNotInList_returnsFalse() {
        assertFalse(uniqueReservationList.contains(ALICE));
    }

    @Test
    public void contains_reservationInList_returnsTrue() {
        uniqueReservationList.add(ALICE);
        assertTrue(uniqueReservationList.contains(ALICE));
    }

    @Test
    public void contains_reservationWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReservationList.add(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE)
                .withOccasions(VALID_OCCASION_BIRTHDAY).build();
        assertTrue(uniqueReservationList.contains(editedAlice));
    }

    @Test
    public void add_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.add(null));
    }

    @Test
    public void add_duplicateReservation_throwsDuplicateReservationException() {
        uniqueReservationList.add(ALICE);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.add(ALICE));
    }

    @Test
    public void setReservation_nullTargetReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservation(null, ALICE));
    }

    @Test
    public void setReservation_nullEditedReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservation(ALICE, null));
    }

    @Test
    public void setReservation_targetReservationNotInList_throwsReservationNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.setReservation(ALICE, ALICE));
    }

    @Test
    public void setReservation_editedReservationIsSameReservation_success() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.setReservation(ALICE, ALICE);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(ALICE);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasSameIdentity_success() {
        uniqueReservationList.add(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE)
                .withOccasions(VALID_OCCASION_BIRTHDAY).build();
        uniqueReservationList.setReservation(ALICE, editedAlice);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(editedAlice);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasDifferentIdentity_success() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.setReservation(ALICE, BOB);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BOB);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservation_editedReservationHasNonUniqueIdentity_throwsDuplicateReservationException() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.add(BOB);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.setReservation(ALICE, BOB));
    }

    @Test
    public void remove_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.remove(null));
    }

    @Test
    public void remove_reservationDoesNotExist_throwsReservationNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.remove(ALICE));
    }

    @Test
    public void remove_existingReservation_removesReservation() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.remove(ALICE);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_nullUniqueReservationList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList
                .setReservations((UniqueReservationList) null));
    }

    @Test
    public void addReservations_reservationsOrderedByDateTime_success() {
        UniqueReservationList reservationList = new UniqueReservationList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        String tomorrow = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.HOURS).format(formatter);
        String dayAfterTomorrow = LocalDateTime.now().plusDays(2).truncatedTo(ChronoUnit.HOURS).format(formatter);

        Reservation earlierReservation = new ReservationBuilder().withDateTime(tomorrow).build();
        Reservation laterReservation = new ReservationBuilder().withDateTime(dayAfterTomorrow).build();

        reservationList.add(laterReservation);
        reservationList.add(earlierReservation);

        List<Reservation> expectedList = Arrays.asList(earlierReservation, laterReservation);
        assertEquals(expectedList, reservationList.asUnmodifiableObservableList());
    }

    @Test
    public void setReservations_uniqueReservationList_replacesOwnListWithProvidedUniqueReservationList() {
        uniqueReservationList.add(ALICE);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BOB);
        uniqueReservationList.setReservations(expectedUniqueReservationList);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservations((List<Reservation>) null));
    }

    @Test
    public void setReservations_list_replacesOwnListWithProvidedList() {
        uniqueReservationList.add(ALICE);
        List<Reservation> reservationList = Collections.singletonList(BOB);
        uniqueReservationList.setReservations(reservationList);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BOB);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setReservations_listWithDuplicateReservations_throwsDuplicateReservationException() {
        List<Reservation> listWithDuplicateReservations = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList
                .setReservations(listWithDuplicateReservations));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueReservationList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueReservationList.asUnmodifiableObservableList().toString(), uniqueReservationList.toString());
    }
}
