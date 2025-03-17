package seedu.reserve.testutil;

import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.reservation.Reservation;

/**
 * A utility class to help with building ReserveMate objects.
 * Example usage: <br>
 *     {@code ReserveMate ab = new ReserveMateBuilder().withCustomer("John", "Doe").build();}
 */
public class ReserveMateBuilder {

    private ReserveMate reserveMate;

    public ReserveMateBuilder() {
        reserveMate = new ReserveMate();
    }

    public ReserveMateBuilder(ReserveMate reserveMate) {
        this.reserveMate = reserveMate;
    }

    /**
     * Adds a new {@code Reservation} to the {@code ReserveMate} that we are building.
     */
    public ReserveMateBuilder withCustomer(Reservation reservation) {
        reserveMate.addReservation(reservation);
        return this;
    }

    public ReserveMate build() {
        return reserveMate;
    }
}
