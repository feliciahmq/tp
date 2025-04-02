package seedu.reserve.testutil;

import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DATETIME_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DINERS_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DINERS_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_ANNIVERSARY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_BIRTHDAY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.reservation.Reservation;

/**
 * A utility class containing a list of {@code Reservation} objects to be used in tests.
 */
public class TypicalReservation {

    public static final Reservation ALICE = new ReservationBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withDiners("5")
            .withDateTime("2025-04-10 1800")
            .withOccasions("friends")
            .withPreference("None")
            .build();

    public static final Reservation BENSON = new ReservationBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withDiners("3")
            .withDateTime("2025-04-25 1200")
            .withOccasions("owesMoney", "friends")
            .withPreference("Extra spicy")
            .build();

    public static final Reservation CARL = new ReservationBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withDiners("2")
            .withDateTime("2025-04-15 1400")
            .withOccasions("Birthday")
            .withPreference("No nuts")
            .build();

    public static final Reservation DANIEL = new ReservationBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withDiners("4")
            .withDateTime("2025-04-20 1600")
            .withOccasions("friends")
            .withPreference("Likes window seats")
            .build();

    public static final Reservation ELLE = new ReservationBuilder().withName("Elle Meyer")
            .withPhone("94822241")
            .withEmail("werner@example.com")
            .withDiners("1")
            .withDateTime("2025-04-05 1000")
            .withOccasions("Birthday")
            .withPreference("Less salty")
            .build();

    public static final Reservation FIONA = new ReservationBuilder().withName("Fiona Kunz")
            .withPhone("94824272")
            .withEmail("lydia@example.com")
            .withDiners("7")
            .withDateTime("2025-04-12 1100")
            .withOccasions("Birthday")
            .withPreference("None")
            .build();

    public static final Reservation GEORGE = new ReservationBuilder().withName("George Best")
            .withPhone("94824423")
            .withEmail("anna@example.com")
            .withDiners("10")
            .withDateTime("2025-04-18 1300")
            .withOccasions("Anniversary")
            .withPreference("More sugar")
            .build();

    public static final Reservation HOON = new ReservationBuilder().withName("Hoon Meier")
            .withPhone("84824244")
            .withEmail("stefan@example.com")
            .withDiners("6")
            .withDateTime("2025-04-07 0900")
            .withOccasions("Birthday")
            .withPreference("None")
            .build();

    public static final Reservation IDA = new ReservationBuilder().withName("Ida Mueller")
            .withPhone("84821315")
            .withEmail("hans@example.com")
            .withDiners("8")
            .withDateTime("2025-04-08 1700")
            .withOccasions("Birthday")
            .withPreference("None")
            .build();

    // Manually added - Reservation's details found in {@code CommandTestUtil}
    public static final Reservation AMY = new ReservationBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withDiners(VALID_DINERS_AMY)
            .withDateTime(VALID_DATETIME_AMY).withOccasions(VALID_OCCASION_ANNIVERSARY).build();
    public static final Reservation BOB = new ReservationBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withDiners(VALID_DINERS_BOB)
            .withDateTime(VALID_DATETIME_BOB).withOccasions(VALID_OCCASION_BIRTHDAY, VALID_OCCASION_ANNIVERSARY)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalReservation() {} // prevents instantiation

    /**
     * Returns an {@code ReserveMate} with all the typical reservations.
     */
    public static ReserveMate getTypicalReserveMate() {
        ReserveMate ab = new ReserveMate();
        for (Reservation reservation : getTypicalReservations()) {
            ab.addReservation(reservation);
        }
        return ab;
    }

    public static List<Reservation> getTypicalReservations() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
