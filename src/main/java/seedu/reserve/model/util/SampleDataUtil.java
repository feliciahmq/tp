package seedu.reserve.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.tag.Occasion;

/**
 * Contains utility methods for populating {@code ReserveMate} with sample data.
 */
public class SampleDataUtil {
    public static Reservation[] getSampleReservations() {
        return new Reservation[]{
            new Reservation(new Name("John Doe"), new Phone("98765432"), new Email("johnd@example.com"),
                new Diners("5"), DateTime.fromFileString("2026-12-31 1800"),
                getTagSet("owesMoney", "friends")),
            new Reservation(new Name("Alexa Yeoh"), new Phone("98765431"), new Email("alexy@example.com"),
                new Diners("5"), DateTime.fromFileString("2026-12-31 1800"),
                getTagSet("owesMoney", "friends")),
            new Reservation(new Name("Mickey Mouse"), new Phone("98765435"), new Email("mickey@example.com"),
                new Diners("5"), DateTime.fromFileString("2026-12-30 1800"),
                getTagSet("owesMoney", "friends"))
        };
    }

    public static ReadOnlyReserveMate getSampleReserveMate() {
        ReserveMate sampleAb = new ReserveMate();
        for (Reservation sampleReservation : getSampleReservations()) {
            sampleAb.addReservation(sampleReservation);

        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Occasion> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Occasion::new)
                .collect(Collectors.toSet());
    }

}
