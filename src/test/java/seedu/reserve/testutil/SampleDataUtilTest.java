package seedu.reserve.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.util.SampleDataUtil;

public class SampleDataUtilTest {
    private static final int NUMBER_OF_RESERVATION = 3;
    @Test
    public void getSampleReservations_returnsExpectedReservations_success() {
        Reservation[] sampleReservations = SampleDataUtil.getSampleReservations();

        assertNotNull(sampleReservations);
        assertEquals(NUMBER_OF_RESERVATION, sampleReservations.length);
    }
}
