package seedu.reserve.logic;

import java.nio.file.Path;
import java.util.HashMap;

import javafx.collections.ObservableList;
import seedu.reserve.commons.core.GuiSettings;
import seedu.reserve.logic.commands.CommandResult;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.reservation.Reservation;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the ReserveMate.
     *
     * @see seedu.reserve.model.Model#getReserveMate()
     */
    ReadOnlyReserveMate getReserveMate();

    /** Returns an unmodifiable view of the filtered list of reservations */
    ObservableList<Reservation> getFilteredReservationList();

    /** Returns a Hash Map of reservation statistics from model */
    HashMap<String, Integer> getReservationStatistics();

    /**
     * Returns the user prefs' reservation book file path.
     */
    Path getReserveMateFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
