package seedu.reserve.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalReservation.ALICE;
import static seedu.reserve.testutil.TypicalReservation.BENSON;
import static seedu.reserve.testutil.TypicalReservation.CARL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.reserve.commons.core.GuiSettings;
import seedu.reserve.model.reservation.NameContainsKeywordsPredicate;
import seedu.reserve.testutil.ReserveMateBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new ReserveMate(), new ReserveMate(modelManager.getReserveMate()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setReserveMateFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setReserveMateFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setReserveMateFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setReserveMateFilePath(null));
    }

    @Test
    public void setReserveMateFilePath_validPath_setsReserveMateFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setReserveMateFilePath(path);
        assertEquals(path, modelManager.getReserveMateFilePath());
    }

    @Test
    public void hasReservation_nullReservation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasReservation(null));
    }

    @Test
    public void hasReservation_reservationNotInReserveMate_returnsFalse() {
        assertFalse(modelManager.hasReservation(ALICE));
    }

    @Test
    public void hasReservation_reservationInReserveMate_returnsTrue() {
        modelManager.addReservation(ALICE);
        assertTrue(modelManager.hasReservation(ALICE));
    }

    @Test
    public void getFilteredReservationList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredReservationList().remove(0));
    }

    @Test
    public void getReservationStatistics_success() {
        ReserveMate reserveMate = new ReserveMateBuilder()
                .withReservation(ALICE)
                .withReservation(BENSON)
                .withReservation(CARL)
                .build();
        UserPrefs userPrefs = new UserPrefs();
        modelManager = new ModelManager(reserveMate, userPrefs);

        HashMap<String, Integer> expectedStatistics = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            expectedStatistics.put(String.valueOf(i), 0);
        }
        expectedStatistics.put("5", 1);
        expectedStatistics.put("3", 1);
        expectedStatistics.put("2", 1);

        assertEquals(modelManager.getReservationStatistics(), expectedStatistics);
    }

    @Test
    public void equals() {
        ReserveMate reserveMate = new ReserveMateBuilder().withReservation(ALICE).withReservation(BENSON).build();
        ReserveMate differentReserveMate = new ReserveMate();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(reserveMate, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(reserveMate, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different ReserveMate -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentReserveMate, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredReservationList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(reserveMate, userPrefs)));

        // different reservationStatistics -> returns false
        modelManager.addReservation(CARL);
        modelManagerCopy.clearReservationStatistics();
        assertFalse(modelManager.equals(modelManagerCopy));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setReserveMateFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(reserveMate, differentUserPrefs)));
    }
}
