package seedu.reserve.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.reservation.UniqueReservationList;

/**
 * Wraps all data at the reservation-book level
 * Duplicates are not allowed (by .isSameReservation comparison)
 */
public class ReserveMate implements ReadOnlyReserveMate {

    private final UniqueReservationList reservations;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        reservations = new UniqueReservationList();
    }

    public ReserveMate() {}

    /**
     * Creates an ReserveMate using the reservations in the {@code toBeCopied}
     */
    public ReserveMate(ReadOnlyReserveMate toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the reservation list with {@code reservations}.
     * {@code reservations} must not contain duplicate reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations.setReservations(reservations);
    }

    /**
     * Resets the existing data of this {@code ReserveMate} with {@code newData}.
     */
    public void resetData(ReadOnlyReserveMate newData) {
        requireNonNull(newData);

        setReservations(newData.getReservationList());
    }

    //// reservation-level operations

    /**
     * Returns true if a reservation with the same identity as {@code reservation} exists in the reservation book.
     */
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reservations.contains(reservation);
    }

    /**
     * Adds a reservation to the reservation book.
     * The reservation must not already exist in the reservation book.
     */
    public void addReservation(Reservation p) {
        reservations.add(p);
    }

    /**
     * Replaces the given reservation {@code target} in the list with {@code editedReservation}.
     * {@code target} must exist in the reservation book.
     * The reservation identity of {@code editedReservation} must not be the same as
     * another existing reservation in the reservation book.
     */
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireNonNull(editedReservation);

        reservations.setReservation(target, editedReservation);
    }

    /**
     * Removes {@code key} from this {@code ReserveMate}.
     * {@code key} must exist in the reservation book.
     */
    public void removeReservation(Reservation key) {
        reservations.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("reservations", reservations)
                .toString();
    }

    @Override
    public ObservableList<Reservation> getReservationList() {
        return reservations.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReserveMate)) {
            return false;
        }

        ReserveMate otherReserveMate = (ReserveMate) other;
        return reservations.equals(otherReserveMate.reservations);
    }

    @Override
    public int hashCode() {
        return reservations.hashCode();
    }
}
