package seedu.reserve.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DinersTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Diners(null));
    }

    @Test
    public void constructor_invalidDiners_throwsIllegalArgumentException() {
        String invalidDiner = "1000";
        assertThrows(IllegalArgumentException.class, () -> new Diners(invalidDiner));
    }

    @Test
    public void constructor_removeLeadingZeros_success() {
        // Remove leading zero
        assertEquals("1", new Diners("00001").value);
        assertEquals("5", new Diners("005").value);
        assertEquals("10", new Diners("010").value);

        assertEquals("1", new Diners("1").value);
        assertEquals("10", new Diners("10").value);
    }

    @Test
    public void isValidDiner() {

        // null diners
        assertThrows(NullPointerException.class, () -> new Diners(null));

        // invalid diners
        assertFalse(Diners.isValidDiners(""));
        assertFalse(Diners.isValidDiners("a"));
        assertFalse(Diners.isValidDiners("a0"));
        assertFalse(Diners.isValidDiners("10000"));
        assertFalse(Diners.isValidDiners("0"));
        assertFalse(Diners.isValidDiners("-1"));

        // valid diners
        assertTrue(Diners.isValidDiners("1"));
        assertTrue(Diners.isValidDiners("10"));
        assertTrue(Diners.isValidDiners("5"));
    }

    @Test
    public void equals() {
        Diners diners = new Diners("1");

        // same values -> true
        assertTrue(diners.equals(new Diners("1")));

        // same object -> return true
        assertTrue(diners.equals(diners));

        // null -> return false
        assertFalse(diners.equals(null));

        // different type -> return false
        assertFalse(diners.equals(5));

        // different values -> return false
        assertFalse(diners.equals(new Diners("2")));
    }
}
