package seedu.reserve.model.occasion;

import static seedu.reserve.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OccasionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Occasion(null));
    }

    @Test
    public void constructor_invalidOccasionName_throwsIllegalArgumentException() {
        String invalidOccasionName = "";
        assertThrows(IllegalArgumentException.class, () -> new Occasion(invalidOccasionName));
    }

    @Test
    public void isValidOccasionName() {
        // null occasion name
        assertThrows(NullPointerException.class, () -> Occasion.isValidOccasionName(null));
    }

}
