package seedu.address.testutil;

import seedu.address.model.ReserveMate;
import seedu.address.model.customer.Customer;

/**
 * A utility class to help with building ReserveMate objects.
 * Example usage: <br>
 *     {@code ReserveMate ab = new ReserveMateBuilder().withCustomer("John", "Doe").build();}
 */
public class ReserveMateBuilder {

    private ReserveMate ReserveMate;

    public ReserveMateBuilder() {
        ReserveMate = new ReserveMate();
    }

    public ReserveMateBuilder(ReserveMate ReserveMate) {
        this.ReserveMate = ReserveMate;
    }

    /**
     * Adds a new {@code Customer} to the {@code ReserveMate} that we are building.
     */
    public ReserveMateBuilder withCustomer(Customer customer) {
        ReserveMate.addCustomer(customer);
        return this;
    }

    public ReserveMate build() {
        return ReserveMate;
    }
}
