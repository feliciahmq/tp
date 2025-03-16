package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyReserveMate;
import seedu.address.model.ReserveMate;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.DateTime;
import seedu.address.model.customer.Diners;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ReserveMate} with sample data.
 */
public class SampleDataUtil {
    public static Customer[] getSampleCustomers() {
        return new Customer[]{
            new Customer(new Name("John Doe"), new Phone("6598765432"), new Email("johnd@example.com"),
                new Address("311, Clementi Ave 2, #02-25"),
                new Diners("5"), new DateTime("2026-12-31 1800"),
                getTagSet("owesMoney", "friends")),
            new Customer(new Name("Alexa Yeoh"), new Phone("6598765432"), new Email("alexy@example.com"),
                new Address("311, Clementi Ave 2, #02-25"),
                new Diners("5"), new DateTime("2026-12-31 1800"),
                getTagSet("owesMoney", "friends")),
            new Customer(new Name("Mickey Mouse"), new Phone("6598765432"), new Email("mickey@example.com"),
                new Address("311, Clementi Ave 2, #02-25"),
                new Diners("5"), new DateTime("2026-12-30 1800"),
                getTagSet("owesMoney", "friends"))
        };
    }

    public static ReadOnlyReserveMate getSampleReserveMate() {
        ReserveMate sampleAb = new ReserveMate();
        for (Customer sampleCustomer : getSampleCustomers()) {
            sampleAb.addCustomer(sampleCustomer);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
