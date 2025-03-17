package seedu.reserve.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalReservation.ALICE;
import static seedu.reserve.testutil.TypicalReservation.BOB;

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
    public void contains_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.contains(null));
    }

    @Test
    public void contains_customerNotInList_returnsFalse() {
        assertFalse(uniqueReservationList.contains(ALICE));
    }

    @Test
    public void contains_customerInList_returnsTrue() {
        uniqueReservationList.add(ALICE);
        assertTrue(uniqueReservationList.contains(ALICE));
    }

    @Test
    public void contains_customerWithSameIdentityFieldsInList_returnsTrue() {
        uniqueReservationList.add(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueReservationList.contains(editedAlice));
    }

    @Test
    public void add_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.add(null));
    }

    @Test
    public void add_duplicateCustomer_throwsDuplicateCustomerException() {
        uniqueReservationList.add(ALICE);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.add(ALICE));
    }

    @Test
    public void setCustomer_nullTargetReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservation(null, ALICE));
    }

    @Test
    public void setCustomer_nullEditedReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.setReservation(ALICE, null));
    }

    @Test
    public void setCustomer_targetCustomerNotInList_throwsReservationNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.setReservation(ALICE, ALICE));
    }

    @Test
    public void setCustomer_editedReservationIsSameReservation_success() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.setReservation(ALICE, ALICE);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(ALICE);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setCustomer_editedReservationHasSameIdentity_success() {
        uniqueReservationList.add(ALICE);
        Reservation editedAlice = new ReservationBuilder(ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        uniqueReservationList.setReservation(ALICE, editedAlice);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(editedAlice);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setCustomer_editedReservationHasDifferentIdentity_success() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.setReservation(ALICE, BOB);
        UniqueReservationList expectedUniqueReservationList = new UniqueReservationList();
        expectedUniqueReservationList.add(BOB);
        assertEquals(expectedUniqueReservationList, uniqueReservationList);
    }

    @Test
    public void setCustomer_editedCustomerHasNonUniqueIdentity_throwsDuplicateReservationException() {
        uniqueReservationList.add(ALICE);
        uniqueReservationList.add(BOB);
        assertThrows(DuplicateReservationException.class, () -> uniqueReservationList.setReservation(ALICE, BOB));
    }

    @Test
    public void remove_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReservationList.remove(null));
    }

    @Test
    public void remove_customerDoesNotExist_throwsCustomerNotFoundException() {
        assertThrows(ReservationNotFoundException.class, () -> uniqueReservationList.remove(ALICE));
    }

    @Test
    public void remove_existingCustomer_removesCustomer() {
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
    public void setReservations_uniqueCustomerList_replacesOwnListWithProvidedUniqueReservationList() {
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
    public void setCustomers_listWithDuplicateReservations_throwsDuplicateReservationException() {
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
