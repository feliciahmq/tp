package seedu.reserve.model;

import java.nio.file.Path;

import seedu.reserve.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getReserveMateFilePath();

}
