package seedu.reserve.model.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NameTest {
    private Name name1;
    private Name name2;
    private Name name3;
    private Name name4;
    private Name name5;

    @BeforeEach
    public void setUp() {
        name1 = new Name("Valid Name");
        name2 = new Name("Valid Name");
        name3 = new Name("Another Valid Name");
        name4 = new Name("JohN DoE");
        name5 = new Name("jOhN DoE");
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }


    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("12345")); // contains numbers only
        assertFalse(Name.isValidName("peter the 2nd")); // contains alphanumeric characters
        assertFalse(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // contains number long names

        // long name more than 50 characters
        assertFalse(Name.isValidName("Lorem ipsum dolor sit amete consectetuer adipiscing"));

        // valid name

        // long name with 50 characters
        assertTrue(Name.isValidName("Lorem ipsum dolor sit amet consectetuer adipiscing"));
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
    }

    @Test
    public void equals() {

        // same value, different capitalization -> True
        assertEquals(name5, name4);

        // same values -> returns true
        assertTrue(name1.equals(name2));

        // same object -> returns true
        assertTrue(name1.equals(name1));

        // null -> returns false
        assertFalse(name1.equals(null));

        // different types -> returns false
        assertFalse(name1.equals(5.0f));

        // different values -> returns false
        assertFalse(name1.equals(name3));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(name1.hashCode(), name2.hashCode());
    }
}
