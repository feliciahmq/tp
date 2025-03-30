package seedu.reserve.testutil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.util.SampleDataUtil;

/**
 * A utility class to help with building Reservation objects.
 */
public class ReservationBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_DINERS = "2";
    public static final String DEFAULT_PREFERENCE = "None";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    public static final String DEFAULT_DATETIME = LocalDateTime.now().plusDays(29)
            .truncatedTo(ChronoUnit.HOURS)
            .format(FORMATTER);

    private Name name;
    private Phone phone;
    private Email email;
    private Diners diners;
    private DateTime dateTime;
    private Set<Occasion> occasions;
    private String preference;

    /**
     * Creates a {@code ReservationBuilder} with the default details.
     */
    public ReservationBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        diners = new Diners(DEFAULT_DINERS);
        dateTime = new DateTime(DEFAULT_DATETIME);
        occasions = new HashSet<>();
        preference = DEFAULT_PREFERENCE;
    }

    /**
     * Initializes the ReservationBuilder with the data of {@code ReservationToCopy}.
     */
    public ReservationBuilder(Reservation reservationToCopy) {
        name = reservationToCopy.getName();
        phone = reservationToCopy.getPhone();
        email = reservationToCopy.getEmail();
        diners = reservationToCopy.getDiners();
        dateTime = reservationToCopy.getDateTime();
        occasions = new HashSet<>(reservationToCopy.getOccasions());
        preference = reservationToCopy.getPreference();
    }

    /**
     * Sets the {@code Name} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code occasions} into a {@code Set<Occasion>}
     * and set it to the {@code Reservation} that we are building.
     */
    public ReservationBuilder withOccasions(String ... occasions) {
        this.occasions = SampleDataUtil.getOccasionSet(occasions);
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

    /**
     * Sets the {@code Preference} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withPreference(String preference) {
        this.preference = preference;
        return this;
    }

    public Reservation build() {
        return new Reservation(name, phone, email, diners, dateTime, occasions, preference);
    }
}
