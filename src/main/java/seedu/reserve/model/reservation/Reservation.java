package seedu.reserve.model.reservation;

import static seedu.reserve.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.model.tag.Occasion;

/**
 * Represents a reservation in the reservation book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reservation {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Diners diners;
    private final DateTime dateTime;
    private final Set<Occasion> occasions = new HashSet<>();
    private final String preference; // New field for customer preferences

    /**
     * Every field must be present and not null.
     */
    public Reservation(Name name, Phone phone, Email email, Diners diners,
                    DateTime dateTime, Set<Occasion> occasions) {
        requireAllNonNull(name, phone, email, diners, occasions);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.diners = diners;
        this.dateTime = dateTime;
        this.occasions.addAll(occasions);
        this.preference = "None"; // Initialize with empty preference
    }

    /**
     * Every field must be present and not null.
     * This constructor includes preference.
     */
    public Reservation(Name name, Phone phone, Email email, Diners diners,
                       DateTime dateTime, Set<Occasion> occasions, String preference) {
        requireAllNonNull(name, phone, email, diners, occasions);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.diners = diners;
        this.dateTime = dateTime;
        this.occasions.addAll(occasions);
        this.preference = preference;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }


    public Diners getDiners() {
        return diners;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Occasion> getTags() {
        return Collections.unmodifiableSet(occasions);
    }

    public String getPreference() {
        return preference;
    }

    /**
     * Returns a copy of this reservation with the updated preference.
     */
    public Reservation withPreference(String preference) {
        Objects.requireNonNull(preference);
        return new Reservation(this.name, this.phone, this.email,
            this.diners, this.dateTime, this.occasions, preference);
    }

    /**
     * Returns true if both reservations have the phone number and reservation date and time.
     * This defines a weaker notion of equality between two reservations.
     */
    public boolean isSameReservation(Reservation otherReservation) {
        if (otherReservation == this) {
            return true;
        }

        return otherReservation != null
                && otherReservation.getName().equals(getName())
                && otherReservation.getPhone().equals(getPhone())
                && otherReservation.getDateTime().equals(getDateTime());
    }

    /**
     * Returns true if both reservations have the same identity and data fields.
     * This defines a stronger notion of equality between two reservations.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Reservation)) {
            return false;
        }

        Reservation otherReservation = (Reservation) other;
        return name.equals(otherReservation.name)
                && phone.equals(otherReservation.phone)
                && email.equals(otherReservation.email)
                && diners.equals(otherReservation.diners)
                && dateTime.equals(otherReservation.dateTime)
                && occasions.equals(otherReservation.occasions)
                && preference.equals(otherReservation.preference);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, diners, dateTime, occasions, preference);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("diners", diners)
                .add("dateTime", dateTime)
                .add("occasion", occasions)
                .add("preference", preference)
                .toString();
    }

}
