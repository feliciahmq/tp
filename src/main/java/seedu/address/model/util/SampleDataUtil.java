package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.DateTime;
import seedu.address.model.customer.Diners;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Customer[] getSampleCustomers() {
        return new Customer[]{
            new Customer(new Name("Alex Yeoh"), new Phone("6587438807"), new Email("alexyeoh@example.com"),
                  new Diners("4"), new DateTime("2025-03-15 1830"),
                  getTagSet("friends")),
            new Customer(new Name("Bernice Yu"), new Phone("6599272758"), new Email("berniceyu@example.com"),
                  new Diners("2"), new DateTime("2025-04-10 1200"),
                  getTagSet("colleagues", "friends")),
            new Customer(new Name("Charlotte Oliveiro"), new Phone("6593210283"), new Email("charlotte@example.com"),
                  new Diners("6"), new DateTime("2025-05-05 2000"),
                  getTagSet("neighbours")),
            new Customer(new Name("David Li"), new Phone("6591031282"), new Email("lidavid@example.com"),
                  new Diners("3"), new DateTime("2025-06-22 1700"),
                  getTagSet("family")),
            new Customer(new Name("Irfan Ibrahim"), new Phone("6592492021"), new Email("irfan@example.com"),
                  new Diners("5"), new DateTime("2025-07-18 1930"),
                  getTagSet("classmates")),
            new Customer(new Name("Roy Balakrishnan"), new Phone("6592624417"), new Email("royb@example.com"),
                  new Diners("1"), new DateTime("2025-08-30 2100"),
                  getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
