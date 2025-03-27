package seedu.reserve.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.reserve.commons.core.GuiSettings;
import seedu.reserve.model.reservation.Reservation;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Reservation> PREDICATE_SHOW_ALL_RESERVATIONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' reservation book file path.
     */
    Path getReserveMateFilePath();

    /**
     * Sets the user prefs' reservation book file path.
     */
    void setReserveMateFilePath(Path reserveMateFilePath);

    /**
     * Replaces reservation book data with the data in {@code ReserveMate}.
     */
    void setReserveMate(ReadOnlyReserveMate reserveMate);

    /** Returns the ReserveMate */
    ReadOnlyReserveMate getReserveMate();

    /**
     * Returns true if a reservation with the same identity as {@code reservation} exists in the reservation book.
     */
    boolean hasReservation(Reservation reservation);

    /**
     * Deletes the given reservation.
     * The reservation must exist in the reservation book.
     */
    void deleteReservation(Reservation target);

    /**
     * Adds the given reservation.
     * {@code reservation} must not already exist in the reservation book.
     */
    void addReservation(Reservation reservation);

    /**
     * Replaces the given reservation {@code target} with {@code editedReservation}.
     * {@code target} must exist in the reservation book.
     * The reservation identity of {@code editedReservation} must not be the same as
     * another existing reservation in the reservation book.
     */
    void setReservation(Reservation target, Reservation editedReservation);

    /** Returns an unmodifiable view of the filtered reservation list */
    ObservableList<Reservation> getFilteredReservationList();

    /** Returns a HashMap of the reservation summary */
    HashMap<String, Integer> getReservationSummary();

    /**
     * Updates the filter of the filtered reservation list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReservationList(Predicate<Reservation> predicate);

    /**
     * Updates the reservation summary in ReserveMate.
     */
    void updateReservationSummary();
}
