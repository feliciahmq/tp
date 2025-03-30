package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX;
import static seedu.reserve.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_OCC_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.reserve.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.OCC_DESC_ANNIVERSARY;
import static seedu.reserve.logic.commands.CommandTestUtil.OCC_DESC_BIRTHDAY;
import static seedu.reserve.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_ANNIVERSARY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_BIRTHDAY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.reserve.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_SECOND_RESERVATION;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_THIRD_RESERVATION;

import org.junit.jupiter.api.Test;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.EditCommand;
import seedu.reserve.logic.commands.EditCommand.EditReservationDescriptor;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.testutil.EditReservationDescriptorBuilder;

public class EditCommandParserTest {

    private static final String OCCASION_EMPTY = " " + PREFIX_OCCASION;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);

        //postive index with a +
        assertParseFailure(parser, "+1" + NAME_DESC_AMY, MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_OCC_DESC, Occasion.MESSAGE_OCCASION_CONSTRAINTS); // invalid occasion

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_OCCASION} alone will reset the occasions of the {@code Reservation} being edited,
        // parsing it together with a valid occasion results in error
        assertParseFailure(parser, "1" + OCC_DESC_ANNIVERSARY + OCC_DESC_BIRTHDAY
            + OCCASION_EMPTY, Occasion.MESSAGE_OCCASION_CONSTRAINTS);
        assertParseFailure(parser, "1" + OCC_DESC_ANNIVERSARY + OCCASION_EMPTY
            + OCC_DESC_BIRTHDAY, Occasion.MESSAGE_OCCASION_CONSTRAINTS);
        assertParseFailure(parser, "1" + OCCASION_EMPTY + OCC_DESC_ANNIVERSARY
            + OCC_DESC_BIRTHDAY, Occasion.MESSAGE_OCCASION_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RESERVATION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + OCC_DESC_BIRTHDAY
                + EMAIL_DESC_AMY + NAME_DESC_AMY + OCC_DESC_ANNIVERSARY;

        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withOccasions(VALID_OCCASION_BIRTHDAY, VALID_OCCASION_ANNIVERSARY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RESERVATION;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RESERVATION;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditReservationDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);


        // occasions
        userInput = targetIndex.getOneBased() + OCC_DESC_ANNIVERSARY;
        descriptor = new EditReservationDescriptorBuilder().withOccasions(VALID_OCCASION_ANNIVERSARY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonOccasionValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_RESERVATION;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY + OCC_DESC_ANNIVERSARY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + OCC_DESC_ANNIVERSARY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + OCC_DESC_BIRTHDAY;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }

    @Test
    public void parse_resetOccasions_success() {
        Index targetIndex = INDEX_THIRD_RESERVATION;
        String userInput = targetIndex.getOneBased() + OCCASION_EMPTY;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder().withOccasions().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
