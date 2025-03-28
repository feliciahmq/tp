package seedu.reserve.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.reserve.commons.core.GuiSettings;
import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonReserveMateStorage reserveMateStorage = new JsonReserveMateStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(reserveMateStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void reserveMateReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonReserveMateStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonReserveMateStorageTest} class.
         */
        ReserveMate original = getTypicalReserveMate();
        storageManager.saveReserveMate(original);
        ReadOnlyReserveMate retrieved = storageManager.readReserveMate().get();
        assertEquals(original, new ReserveMate(retrieved));
    }

    @Test
    public void getReserveMateFilePath() {
        assertNotNull(storageManager.getReserveMateFilePath());
    }

}
