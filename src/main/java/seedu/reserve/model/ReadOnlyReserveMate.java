package seedu.reserve.model;

import javafx.collections.ObservableList;
import seedu.reserve.model.customer.Customer;

/**
 * Unmodifiable view of an reservation book
 */
public interface ReadOnlyReserveMate {

    /**
     * Returns an unmodifiable view of the customers list.
     * This list will not contain any duplicate customers.
     */
    ObservableList<Customer> getCustomerList();

}
