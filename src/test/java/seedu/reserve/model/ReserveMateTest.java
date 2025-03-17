package seedu.reserve.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalCustomers.ALICE;
import static seedu.reserve.testutil.TypicalCustomers.getTypicalReserveMate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.reserve.model.customer.Customer;
import seedu.reserve.model.customer.exceptions.DuplicateCustomerException;
import seedu.reserve.testutil.CustomerBuilder;

public class ReserveMateTest {

    private final ReserveMate reserveMate = new ReserveMate();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), reserveMate.getCustomerList());
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
        // Two customers with the same identity fields
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Customer> newCustomers = Arrays.asList(ALICE, editedAlice);
        ReserveMateStub newData = new ReserveMateStub(newCustomers);

        assertThrows(DuplicateCustomerException.class, () -> reserveMate.resetData(newData));
    }

    @Test
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reserveMate.hasCustomer(null));
    }

    @Test
    public void hasCustomer_customerNotInReserveMate_returnsFalse() {
        assertFalse(reserveMate.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInReserveMate_returnsTrue() {
        reserveMate.addCustomer(ALICE);
        assertTrue(reserveMate.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerWithSameIdentityFieldsInReserveMate_returnsTrue() {
        reserveMate.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(reserveMate.hasCustomer(editedAlice));
    }

    @Test
    public void getCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> reserveMate.getCustomerList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ReserveMate.class.getCanonicalName() + "{customers=" + reserveMate.getCustomerList() + "}";
        assertEquals(expected, reserveMate.toString());
    }

    /**
     * A stub ReadOnlyReserveMate whose customers list can violate interface constraints.
     */
    private static class ReserveMateStub implements ReadOnlyReserveMate {
        private final ObservableList<Customer> customers = FXCollections.observableArrayList();

        ReserveMateStub(Collection<Customer> customers) {
            this.customers.setAll(customers);
        }

        @Override
        public ObservableList<Customer> getCustomerList() {
            return customers;
        }
    }

}
