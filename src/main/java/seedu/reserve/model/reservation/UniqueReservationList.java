package seedu.reserve.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.reserve.model.reservation.exceptions.DuplicateReservationException;
import seedu.reserve.model.reservation.exceptions.ReservationNotFoundException;

/**
 * A list of reservations that enforces uniqueness between its elements and does not allow nulls.
 * A reservation is considered unique by comparing using {@code reservation#isSameReservation(reservation)}.
 * As such, adding and updating of reservations uses Reservation#isSameReservation(Reservation) for equality
 * so as to ensure that the reservation being added or updated is unique in terms of identity in the
 * UniqueReservationList. However, the removal of a reservation uses Reservation#equals(Object) so
 * as to ensure that the reservation with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Reservation#isSameReservation(Reservation)
 */
public class UniqueReservationList implements Iterable<Reservation> {

    private final Set<Reservation> internalSet = new TreeSet<>((r1, r2) -> {
        int compareResult = r1.getDateTime().compareTo(r2.getDateTime());
        if (compareResult == 0) {
            return 0; // Consider duplicate if same date and time
        }
        return compareResult;
    });

    private final ObservableList<Reservation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reservation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent reservation as the given argument.
     */
    public boolean contains(Reservation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameReservation);
    }

    /**
     * Adds a reservation to the list.
     * The reservation must not already exist in the list.
     */
    public void add(Reservation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReservationException();
        }
        int idx = findInsertIndex(toAdd);
        internalList.add(idx, toAdd);
    }

    /**
     * Find index of the list for the reservation to insert into based on the DateTime.
     * Ensure list is remains sorted in ascending order.
     *
     * @param toInsert The reservation to be inserted into the list.
     * @return The index of the location the reservation will be inserted.
     * */
    private int findInsertIndex(Reservation toInsert) {
        int idx = 0;
        while (idx < internalList.size() && compareDateTime(internalList.get(idx), toInsert)) {
            idx++;
        }
        return idx;
    }

    /**
     * Compare DateTime of two reservation.
     * @param reservation The current reservation in the list.
     * @param toInsert The new reservation to be inserted.
     * @retun {@code true} if {@code reservation} has earlier DateTime than {@code toInsert}, otherwise {@code false}.
     * */
    private boolean compareDateTime(Reservation reservation, Reservation toInsert) {
        return reservation.getDateTime().compareTo(toInsert.getDateTime()) < 0;
    }

    /**
     * Replaces the reservation {@code target} in the list with {@code editedReservation}.
     * {@code target} must exist in the list.
     * The reservation identity of {@code editedReservation} must not be the same as another existing reservation
     * in the list.
     */
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReservationNotFoundException();
        }

        if (!target.isSameReservation(editedReservation) && contains(editedReservation)) {
            throw new DuplicateReservationException();
        }
        internalList.remove(index);
        int idx = findInsertIndex(editedReservation);
        internalList.add(idx, editedReservation);
    }

    /**
     * Removes the equivalent reservation from the list.
     * The reservation must exist in the list.
     */
    public void remove(Reservation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReservationNotFoundException();
        }
    }

    public void setReservations(UniqueReservationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code reservations}.
     * {@code reservations} must not contain duplicate reservations.
     */
    public void setReservations(List<Reservation> reservations) {
        requireAllNonNull(reservations);
        if (!reservationsAreUnique(reservations)) {
            throw new DuplicateReservationException();
        }

        internalList.setAll(reservations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reservation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Reservation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueReservationList)) {
            return false;
        }

        UniqueReservationList otherUniqueReservationList = (UniqueReservationList) other;
        return internalList.equals(otherUniqueReservationList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code reservations} contains only unique reservations.
     */
    private boolean reservationsAreUnique(List<Reservation> reservations) {
        for (int i = 0; i < reservations.size() - 1; i++) {
            for (int j = i + 1; j < reservations.size(); j++) {
                if (reservations.get(i).isSameReservation(reservations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
