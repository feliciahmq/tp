package seedu.reserve.testutil;

import static seedu.reserve.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DINERS_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

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
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withDiners("5")
            .withDateTime("2040-10-10 1800")
            .withTags("friends")
            .build();

    public static final Reservation BENSON = new ReservationBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withDiners("3")
            .withDateTime("2040-12-25 1200")
            .withTags("owesMoney", "friends")
            .build();

    public static final Reservation CARL = new ReservationBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withDiners("2")
            .withDateTime("2040-11-15 1400")
            .build();

    public static final Reservation DANIEL = new ReservationBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withDiners("4")
            .withDateTime("2040-09-20 1600")
            .withTags("friends")
            .build();

    public static final Reservation ELLE = new ReservationBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withDiners("1")
            .withDateTime("2040-08-05 1000")
            .build();

    public static final Reservation FIONA = new ReservationBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withDiners("7")
            .withDateTime("2040-07-12 1100")
            .build();

    public static final Reservation GEORGE = new ReservationBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withDiners("10")
            .withDateTime("2040-06-18 1300")
            .build();

    // Manually added
    public static final Reservation HOON = new ReservationBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withDiners("6")
            .withDateTime("2040-05-01 0900")
            .build();

    public static final Reservation IDA = new ReservationBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withDiners("8")
            .withDateTime("2040-04-15 1700")
            .build();

    // Manually added - Reservation's details found in {@code CommandTestUtil}
    public static final Reservation AMY = new ReservationBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Reservation BOB = new ReservationBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withDiners(VALID_DINERS_BOB)
            .withDateTime(VALID_DATETIME_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalReservation() {} // prevents instantiation

    /**
     * Returns an {@code ReserveMate} with all the typical customers.
     */
    public static ReserveMate getTypicalReserveMate() {
        ReserveMate ab = new ReserveMate();
        for (Reservation reservation : getTypicalCustomers()) {
            ab.addReservation(reservation);
        }
        return ab;
    }

    public static List<Reservation> getTypicalCustomers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
