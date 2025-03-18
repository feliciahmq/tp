package seedu.reserve.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.tag.Tag;
import seedu.reserve.model.util.SampleDataUtil;

/**
 * A utility class to help with building Reservation objects.
 */
public class ReservationBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DINERS = "2";
    public static final String DEFAULT_DATETIME = "2026-12-12 1800";

    private Name name;
    private Phone phone;
    private Email email;
    private Diners diners;
    private DateTime dateTime;
    private Set<Tag> tags;

    /**
     * Creates a {@code ReservationBuilder} with the default details.
     */
    public ReservationBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        diners = new Diners(DEFAULT_DINERS);
        dateTime = new DateTime(DEFAULT_DATETIME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ReservationBuilder with the data of {@code CustomerToCopy}.
     */
    public ReservationBuilder(Reservation reservationToCopy) {
        name = reservationToCopy.getName();
        phone = reservationToCopy.getPhone();
        email = reservationToCopy.getEmail();
        diners = reservationToCopy.getDiners();
        dateTime = reservationToCopy.getDateTime();
        tags = new HashSet<>(reservationToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Reservation} that we are building.
     */
    public ReservationBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Diners} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withDiners(String diners) {
        this.diners = new Diners(diners);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withDateTime(String dateTime) {
        this.dateTime = DateTime.fromFileString(dateTime);
        return this;
    }



    public Reservation build() {
        return new Reservation(name, phone, email, diners, dateTime, tags);
    }

}
