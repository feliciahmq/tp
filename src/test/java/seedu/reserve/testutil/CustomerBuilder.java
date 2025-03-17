package seedu.reserve.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.reserve.model.customer.Customer;
import seedu.reserve.model.customer.DateTime;
import seedu.reserve.model.customer.Diners;
import seedu.reserve.model.customer.Email;
import seedu.reserve.model.customer.Name;
import seedu.reserve.model.customer.Phone;
import seedu.reserve.model.tag.Tag;
import seedu.reserve.model.util.SampleDataUtil;

/**
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_DINERS = "2";
    public static final String DEFAULT_DATETIME = "2026-12-12 1800";

    private Name name;
    private Phone phone;
    private Email email;
    private Diners diners;
    private DateTime dateTime;
    private Set<Tag> tags;

    /**
     * Creates a {@code CustomerBuilder} with the default details.
     */
    public CustomerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        diners = new Diners(DEFAULT_DINERS);
        dateTime = new DateTime(DEFAULT_DATETIME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CustomerBuilder with the data of {@code CustomerToCopy}.
     */
    public CustomerBuilder(Customer customerToCopy) {
        name = customerToCopy.getName();
        phone = customerToCopy.getPhone();
        email = customerToCopy.getEmail();
        diners = customerToCopy.getDiners();
        dateTime = customerToCopy.getDateTime();
        tags = new HashSet<>(customerToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Customer} that we are building.
     */
    public CustomerBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Diners} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withDiners(String diners) {
        this.diners = new Diners(diners);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }



    public Customer build() {
        return new Customer(name, phone, email, diners, dateTime, tags);
    }

}
