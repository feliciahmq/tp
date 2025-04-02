package seedu.reserve.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Reservation;


/**
 * Contains utility methods for populating {@code ReserveMate} with sample data.
 */

public class SampleDataUtil {
    private static String dateTimeTomorrow;
    private static String dateTimeNextMonth;
    private static String dateTimePlusFiftyDays;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    public static Reservation[] getSampleReservations() {
        setDateTime();
        return new Reservation[]{
            new Reservation(new Name("John Doe"), new Phone("98765432"), new Email("johnd@example.com"),
                new Diners("5"), DateTime.fromFileString(dateTimeTomorrow),
                getOccasionSet("Anniversary", "Birthday")),
            new Reservation(new Name("Alexa Yeoh"), new Phone("98765431"), new Email("alexy@example.com"),
                new Diners("5"), DateTime.fromFileString(dateTimeNextMonth),
                getOccasionSet("Graduation", "Christmas")),
            new Reservation(new Name("Mickey Mouse"), new Phone("98765435"), new Email("mickey@example.com"),
                new Diners("5"), DateTime.fromFileString(dateTimePlusFiftyDays),
                getOccasionSet("CNY", "Reunion"))
        };
    }

    private static void setDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        dateTimeTomorrow = dateTime.plusDays(1).withHour(8).withMinute(0).withSecond(0).format(FORMATTER);
        dateTimeNextMonth = dateTime.plusDays(31).withHour(10).withMinute(0).withSecond(0).format(FORMATTER);
        dateTimePlusFiftyDays = dateTime.plusDays(50).withHour(20).withMinute(0).withSecond(0).format(FORMATTER);


    }

    public static ReadOnlyReserveMate getSampleReserveMate() {
        ReserveMate sampleAb = new ReserveMate();
        for (Reservation sampleReservation : getSampleReservations()) {
            sampleAb.addReservation(sampleReservation);

        }
        return sampleAb;
    }

    /**
     * Returns an occasion set containing the list of strings given.
     */
    public static Set<Occasion> getOccasionSet(String... strings) {
        return Arrays.stream(strings)
                .map(Occasion::new)
                .collect(Collectors.toSet());
    }
}
