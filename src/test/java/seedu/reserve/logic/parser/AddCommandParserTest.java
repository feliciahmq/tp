package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.commands.CommandTestUtil.DATETIME_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.DATETIME_DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.DINERS_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.DINERS_DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.reserve.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.reserve.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.reserve.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DINERS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.reserve.testutil.TypicalReservation.AMY;
import static seedu.reserve.testutil.TypicalReservation.BOB;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.AddCommand;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.tag.Tag;
import seedu.reserve.testutil.ReservationBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reservation expectedReservation = new ReservationBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedReservation));


        // multiple tags - all accepted
        Reservation expectedReservationMultipleTags = new ReservationBuilder(BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + DINERS_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedReservationMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedReservationString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedReservationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedReservationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedReservationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));


        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedReservationString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedReservationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE,
                        PREFIX_DATE_TIME, PREFIX_NUMBER_OF_DINERS));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedReservationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedReservationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedReservationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid name
        assertParseFailure(parser, validExpectedReservationString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedReservationString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedReservationString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Reservation expectedReservation = new ReservationBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + DINERS_DESC_AMY + DATETIME_DESC_AMY, new AddCommand(expectedReservation));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + DINERS_DESC_BOB
                + DATETIME_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB
                        + DINERS_DESC_BOB + DATETIME_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB, expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + DINERS_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);


        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + DINERS_DESC_BOB + DATETIME_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + DINERS_DESC_BOB + DATETIME_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
