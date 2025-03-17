package seedu.reserve.model;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.reserve.commons.core.GuiSettings;
import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.model.reservation.Reservation;

/**
 * Represents the in-memory model of the reservation book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ReserveMate reserveMate;
    private final UserPrefs userPrefs;
    private final FilteredList<Reservation> filteredReservations;

    /**
     * Initializes a ModelManager with the given ReserveMate and userPrefs.
     */
    public ModelManager(ReadOnlyReserveMate reserveMate, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(reserveMate, userPrefs);

        logger.fine("Initializing with reservation book: " + reserveMate + " and user prefs " + userPrefs);

        this.reserveMate = new ReserveMate(reserveMate);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredReservations = new FilteredList<>(this.reserveMate.getReservationList());
    }

    public ModelManager() {
        this(new ReserveMate(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getReserveMateFilePath() {
        return userPrefs.getReserveMateFilePath();
    }

    @Override
    public void setReserveMateFilePath(Path reserveMateFilePath) {
        requireNonNull(reserveMateFilePath);
        userPrefs.setReserveMateFilePath(reserveMateFilePath);
    }

    //=========== ReserveMate ================================================================================

    @Override
    public void setReserveMate(ReadOnlyReserveMate reserveMate) {
        this.reserveMate.resetData(reserveMate);
    }

    @Override
    public ReadOnlyReserveMate getReserveMate() {
        return reserveMate;
    }

    @Override
    public boolean hasReservation(Reservation reservation) {
        requireNonNull(reservation);
        return reserveMate.hasReservation(reservation);
    }

    @Override
    public void deleteReservation(Reservation target) {
        reserveMate.removeReservation(target);
    }

    @Override
    public void addReservation(Reservation reservation) {
        reserveMate.addReservation(reservation);
        updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
    }

    @Override
    public void setReservation(Reservation target, Reservation editedReservation) {
        requireAllNonNull(target, editedReservation);

        reserveMate.setReservation(target, editedReservation);
    }

    //=========== Filtered Reservation List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Reservation} backed by the internal list of
     * {@code versionedReserveMate}
     */
    @Override
    public ObservableList<Reservation> getFilteredReservationList() {
        return filteredReservations;
    }

    @Override
    public void updateFilteredReservationList(Predicate<Reservation> predicate) {
        requireNonNull(predicate);
        filteredReservations.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return reserveMate.equals(otherModelManager.reserveMate)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredReservations.equals(otherModelManager.filteredReservations);
    }

}
