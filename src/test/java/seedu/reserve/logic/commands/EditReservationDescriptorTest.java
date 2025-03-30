package seedu.reserve.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.reserve.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_OCCASION_BIRTHDAY;
import static seedu.reserve.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.reserve.logic.commands.EditCommand.EditReservationDescriptor;
import seedu.reserve.testutil.EditReservationDescriptorBuilder;

public class EditReservationDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditReservationDescriptor descriptorWithSameValues = new EditReservationDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditReservationDescriptor editedAmy = new EditReservationDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditReservationDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditReservationDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different occasions -> returns false
        editedAmy = new EditReservationDescriptorBuilder(DESC_AMY).withOccasions(VALID_OCCASION_BIRTHDAY).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditCommand.EditReservationDescriptor editReservationDescriptor = new EditCommand.EditReservationDescriptor();
        String expected = EditCommand.EditReservationDescriptor.class.getCanonicalName() + "{name="
                + editReservationDescriptor.getName().orElse(null) + ", phone="
                + editReservationDescriptor.getPhone().orElse(null) + ", email="
                + editReservationDescriptor.getEmail().orElse(null) + ", diners="
                + editReservationDescriptor.getDiners().orElse(null) + ", dateTime="
                + editReservationDescriptor.getDateTime().orElse(null) + ", occasions="
                + editReservationDescriptor.getOccasions().orElse(null) + "}";
        assertEquals(expected, editReservationDescriptor.toString());
    }
}
