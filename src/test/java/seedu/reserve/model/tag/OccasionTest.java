package seedu.reserve.model.tag;

import static seedu.reserve.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OccasionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Occasion(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Occasion(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Occasion.isValidTagName(null));
    }

}
