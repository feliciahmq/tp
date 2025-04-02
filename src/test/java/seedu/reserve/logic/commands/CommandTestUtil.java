package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DINERS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.reserve.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.reservation.NameContainsKeywordsPredicate;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.testutil.EditReservationDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "99999999";
    public static final String VALID_PHONE_BOB = "88888888";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_DINERS_AMY = "2";
    public static final String VALID_DINERS_BOB = "4";
    public static final String VALID_DATETIME_AMY = LocalDateTime.now()
            .plusDays(30)
            .truncatedTo(ChronoUnit.HOURS)
            .format(FORMATTER);
    public static final String VALID_DATETIME_BOB = LocalDateTime.now().plusDays(29)
            .truncatedTo(ChronoUnit.HOURS)
            .format(FORMATTER);
    public static final String VALID_OCCASION_BIRTHDAY = "Birthday";
    public static final String VALID_OCCASION_ANNIVERSARY = "Anniversary";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String DINERS_DESC_AMY = " " + PREFIX_NUMBER_OF_DINERS + VALID_DINERS_AMY;
    public static final String DINERS_DESC_BOB = " " + PREFIX_NUMBER_OF_DINERS + VALID_DINERS_BOB;
    public static final String DATETIME_DESC_AMY = " " + PREFIX_DATE_TIME + VALID_DATETIME_AMY;
    public static final String DATETIME_DESC_BOB = " " + PREFIX_DATE_TIME + VALID_DATETIME_BOB;
    public static final String OCC_DESC_ANNIVERSARY = " " + PREFIX_OCCASION + VALID_OCCASION_ANNIVERSARY;
    public static final String OCC_DESC_BIRTHDAY = " " + PREFIX_OCCASION + VALID_OCCASION_BIRTHDAY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_DINERS_DESC = " " + PREFIX_NUMBER_OF_DINERS + "0"; // '0' not in range
    public static final String INVALID_DATETIME_DESC = " "
            + PREFIX_DATE_TIME + LocalDateTime.now().plusDays(28)
            .truncatedTo(ChronoUnit.HOURS).format(FORMATTER); // not a valid date time
    public static final String INVALID_OCC_DESC = " " + PREFIX_OCCASION + "graduation*"; // '*' not allowed in occasions

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditReservationDescriptor DESC_AMY;
    public static final EditCommand.EditReservationDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditReservationDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withDiners(VALID_DINERS_AMY).withDateTime(VALID_DATETIME_AMY)
                .withOccasions(VALID_OCCASION_ANNIVERSARY).build();
        DESC_BOB = new EditReservationDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withDiners(VALID_DINERS_BOB).withDateTime(VALID_DATETIME_BOB)
                .withOccasions(VALID_OCCASION_BIRTHDAY, VALID_OCCASION_ANNIVERSARY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the reservation book, filtered reservation list and selected reservation in {@code actualModel}
     * remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ReserveMate expectedReserveMate = new ReserveMate(actualModel.getReserveMate());
        List<Reservation> expectedFilteredList = new ArrayList<>(actualModel.getFilteredReservationList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedReserveMate, actualModel.getReserveMate());
        assertEquals(expectedFilteredList, actualModel.getFilteredReservationList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the reservation at the given {@code targetIndex} in the
     * {@code model}'s reservation book.
     */
    public static void showReservationAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredReservationList().size());

        Reservation reservation = model.getFilteredReservationList().get(targetIndex.getZeroBased());
        final String[] splitName = reservation.getName().fullName.split("\\s+");
        model.updateFilteredReservationList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredReservationList().size());
    }

}
