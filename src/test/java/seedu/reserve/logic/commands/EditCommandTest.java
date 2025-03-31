package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DATETIME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DINERS_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_DINERS_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_BIRTHDAY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.reserve.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.reserve.logic.commands.CommandTestUtil.showReservationAtIndex;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_SECOND_RESERVATION;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_THIRD_RESERVATION;
import static seedu.reserve.testutil.TypicalReservation.getTypicalReserveMate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.testutil.EditReservationDescriptorBuilder;
import seedu.reserve.testutil.ReservationBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private Model model = new ModelManager(getTypicalReserveMate(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        Reservation editedReservation = new ReservationBuilder().build();
        EditCommand.EditReservationDescriptor descriptor =
            new EditReservationDescriptorBuilder(editedReservation).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_RESERVATION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
            Messages.format(editedReservation));

        // Execute the command
        CommandResult result = editCommand.execute(model);

        // Verify the result message
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Get the actual updated reservation from the model
        Reservation updatedReservation = model.getFilteredReservationList()
            .get(INDEX_SECOND_RESERVATION.getZeroBased());

        // Verify the original is no longer present by comparing against typical data
        Reservation originalReservation = new ModelManager(getTypicalReserveMate(), new UserPrefs())
            .getFilteredReservationList().get(INDEX_SECOND_RESERVATION.getZeroBased());
        assertNotEquals(originalReservation, updatedReservation);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastReservation = Index.fromOneBased(model.getFilteredReservationList().size());
        Reservation lastReservation = model.getFilteredReservationList()
                .get(indexLastReservation.getZeroBased());

        ReservationBuilder reservationInList = new ReservationBuilder(lastReservation);
        Reservation editedReservation = reservationInList.withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withDiners(VALID_DINERS_BOB).withDateTime(VALID_DATETIME_BOB)
                .withOccasions(VALID_OCCASION_BIRTHDAY).build();

        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_NAME_BOB).withDiners(VALID_DINERS_BOB).withDateTime(VALID_DATETIME_BOB)
                .withPhone(VALID_PHONE_BOB).withOccasions(VALID_OCCASION_BIRTHDAY).build();
        EditCommand editCommand = new EditCommand(indexLastReservation, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new ReserveMate(model.getReserveMate()),
                new UserPrefs());
        expectedModel.setReservation(lastReservation, editedReservation);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_SECOND_RESERVATION,
                new EditCommand.EditReservationDescriptor());
        Reservation editedReservation = model.getFilteredReservationList()
                .get(INDEX_SECOND_RESERVATION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new ReserveMate(model.getReserveMate()),
                new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showReservationAtIndex(model, INDEX_SECOND_RESERVATION);

        Reservation reservationInFilteredList = model.getFilteredReservationList()
                .get(INDEX_FIRST_RESERVATION.getZeroBased());
        Reservation editedReservation = new ReservationBuilder(reservationInFilteredList)
                .withName(VALID_NAME_BOB)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESERVATION,
                new EditReservationDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
                Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new ReserveMate(model.getReserveMate()),
                new UserPrefs());
        expectedModel.setReservation(model.getFilteredReservationList().get(0), editedReservation);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReservationUnfilteredList_failure() {
        Reservation firstReservation = model.getFilteredReservationList()
                .get(INDEX_FIRST_RESERVATION.getZeroBased());
        EditCommand.EditReservationDescriptor descriptor =
                new EditReservationDescriptorBuilder(firstReservation).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_RESERVATION, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_DUPLICATE_RESERVATION);
    }

    @Test
    public void execute_duplicateReservationFilteredList_failure() {
        showReservationAtIndex(model, INDEX_SECOND_RESERVATION);
        // edit reservation in filtered list into a duplicate in ReserveMate
        Reservation reservationInList = model.getReserveMate().getReservationList()
                .get(INDEX_THIRD_RESERVATION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESERVATION,
                new EditReservationDescriptorBuilder(reservationInList).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_DUPLICATE_RESERVATION);
    }

    @Test
    public void execute_invalidReservationIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index
                .fromOneBased(model.getFilteredReservationList().size() + 1);
        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of reservation book
     */
    @Test
    public void execute_invalidReservationIndexFilteredList_failure() {
        showReservationAtIndex(model, INDEX_FIRST_RESERVATION);
        Index outOfBoundIndex = INDEX_SECOND_RESERVATION;
        // ensures that outOfBoundIndex is still in bounds of ReserveMate list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getReserveMate().getReservationList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditReservationDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_editPastReservation_failure() {
        Reservation pastReservation = new ReservationBuilder().withDateTime("2022-01-01 1200").build();
        model.addReservation(pastReservation);

        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withDateTime(LocalDateTime.now().plusDays(29).truncatedTo(ChronoUnit.HOURS).format(FORMATTER))
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESERVATION, descriptor);

        // Assert that the command fails with the correct error message
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_FUTURE_RESERVATION_REQUIRED);
    }

    @Test
    public void execute_clearOccasions_success() {
        model = new ModelManager(new ReserveMate(), new UserPrefs());
        Reservation futureReservationWithOccasion = new ReservationBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withDiners(VALID_DINERS_AMY)
            .withDateTime(LocalDateTime.now().plusDays(1).format(FORMATTER))
            .withOccasions(VALID_OCCASION_BIRTHDAY)
            .build();

        model.addReservation(futureReservationWithOccasion);
        Index targetIndex = INDEX_FIRST_RESERVATION; // Use first index since we cleared the model

        // Create descriptor to clear occasions
        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
            .withOccasions().build(); // Empty occasions to clear occasions
        EditCommand editCommand = new EditCommand(targetIndex, descriptor);

        // Create the expected reservation after editing (same as original but without occasions)
        Reservation editedReservation = new ReservationBuilder(futureReservationWithOccasion)
            .withOccasions().build();

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESERVATION_SUCCESS,
            Messages.format(editedReservation));

        Model expectedModel = new ModelManager(new ReserveMate(model.getReserveMate()),
            new UserPrefs());
        expectedModel.setReservation(futureReservationWithOccasion, editedReservation);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearOccasionsFromPastReservation_failure() {
        // Create a past reservation with an occasion
        Reservation pastReservationWithOccasion = new ReservationBuilder()
            .withDateTime("2020-01-01 1200") // Past date
            .withOccasions(VALID_OCCASION_BIRTHDAY)
            .build();

        model.setReservation(model.getFilteredReservationList().get(0), pastReservationWithOccasion);

        EditCommand.EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
            .withOccasions().build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESERVATION, descriptor);

        // The command should fail because we can't edit past reservations
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_FUTURE_RESERVATION_REQUIRED);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_RESERVATION, DESC_AMY);

        // same values -> returns true
        EditCommand.EditReservationDescriptor copyDescriptor = new EditCommand.EditReservationDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_RESERVATION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(true)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_RESERVATION, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_RESERVATION, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCommand.EditReservationDescriptor editReservationDescriptor = new EditCommand.EditReservationDescriptor();
        EditCommand editCommand = new EditCommand(index, editReservationDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editReservationDescriptor="
                + editReservationDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
