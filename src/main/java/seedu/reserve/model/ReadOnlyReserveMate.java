package seedu.reserve.model;

import javafx.collections.ObservableList;
import seedu.reserve.model.reservation.Reservation;

/**
 * Unmodifiable view of an reservation book
 */
public interface ReadOnlyReserveMate {

    /**
     * Returns an unmodifiable view of the reservations list.
     * This list will not contain any duplicate reservation.
     */
    ObservableList<Reservation> getReservationList();

}
