package seedu.reserve.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.reserve.commons.exceptions.IllegalValueException;
import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.customer.Customer;

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
     * Converts this reservation book into the model's {@code ReserveMate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReserveMate toModelType() throws IllegalValueException {
        ReserveMate reserveMate = new ReserveMate();
        for (JsonAdaptedCustomer jsonAdaptedCustomer : customers) {
            Customer customer = jsonAdaptedCustomer.toModelType();
            if (reserveMate.hasCustomer(customer)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CUSTOMER);
            }
            reserveMate.addCustomer(customer);
        }
        return reserveMate;
    }

}
