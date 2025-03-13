package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReserveMate;
import seedu.address.model.ReadOnlyReserveMate;
import seedu.address.model.customer.Customer;

/**
 * An Immutable ReserveMate that is serializable to JSON format.
 */
@JsonRootName(value = "ReserveMate")
class JsonSerializableReserveMate {

    public static final String MESSAGE_DUPLICATE_CUSTOMER = "Customers list contains duplicate customer(s).";

    private final List<JsonAdaptedCustomer> customers = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableReserveMate} with the given customers.
     */
    @JsonCreator
    public JsonSerializableReserveMate(@JsonProperty("customers") List<JsonAdaptedCustomer> customers) {
        this.customers.addAll(customers);
    }

    /**
     * Converts a given {@code ReadOnlyReserveMate} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableReserveMate}.
     */
    public JsonSerializableReserveMate(ReadOnlyReserveMate source) {
        customers.addAll(source.getCustomerList().stream().map(JsonAdaptedCustomer::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code ReserveMate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReserveMate toModelType() throws IllegalValueException {
        ReserveMate ReserveMate = new ReserveMate();
        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (ReserveMate.hasCustomer(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            ReserveMate.addCustomer(customer);
        }
        return ReserveMate;
    }

}
