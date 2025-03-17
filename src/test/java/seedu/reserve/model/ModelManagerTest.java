package seedu.reserve.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalCustomers.ALICE;
import static seedu.reserve.testutil.TypicalCustomers.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.reserve.commons.core.GuiSettings;
import seedu.reserve.model.customer.NameContainsKeywordsPredicate;
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
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCustomer(null));
    }

    @Test
    public void hasCustomer_customerNotInReserveMate_returnsFalse() {
        assertFalse(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInReserveMate_returnsTrue() {
        modelManager.addCustomer(ALICE);
        assertTrue(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCustomerList().remove(0));
    }

    @Test
    public void equals() {
        ReserveMate reserveMate = new ReserveMateBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
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
        modelManager.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(reserveMate, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setReserveMateFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(reserveMate, differentUserPrefs)));
    }
}
