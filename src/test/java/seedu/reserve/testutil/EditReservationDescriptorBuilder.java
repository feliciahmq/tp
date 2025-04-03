package seedu.reserve.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.reserve.logic.commands.EditCommand;
import seedu.reserve.logic.commands.EditCommand.EditReservationDescriptor;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Reservation;

/**
 * A utility class to help with building EditReservationDescriptor objects.
 */
public class EditReservationDescriptorBuilder {

    private EditCommand.EditReservationDescriptor descriptor;

    public EditReservationDescriptorBuilder() {
        descriptor = new EditCommand.EditReservationDescriptor();
    }

    public EditReservationDescriptorBuilder(EditCommand.EditReservationDescriptor descriptor) {
        this.descriptor = new EditCommand.EditReservationDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReservationDescriptor} with fields containing {@code reservation}'s details
     */
    public EditReservationDescriptorBuilder(Reservation reservation) {
        descriptor = new EditCommand.EditReservationDescriptor();
        descriptor.setName(reservation.getName());
        descriptor.setPhone(reservation.getPhone());
        descriptor.setEmail(reservation.getEmail());
        descriptor.setDiners(reservation.getDiners());
        descriptor.setDateTime(reservation.getDateTime());
        descriptor.setOccasions(reservation.getOccasions());
    }

    /**
     * Sets the {@code Name} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Diners} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withDiners(String diners) {
        descriptor.setDiners(new Diners(diners));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(new DateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code EditReservationDescriptor} that we are building.
     */
    public EditReservationDescriptorBuilder withEditedDateTime(String dateTime) {
        descriptor.setDateTime(DateTime.fromFileString(dateTime));
        return this;
    }


    /**
     * Parses the {@code occasions} into a {@code Set<Occasion>} and set it to the {@code EditReservationDescriptor}
     * that we are building.
     */
    public EditReservationDescriptorBuilder withOccasions(String... occasions) {
        Set<Occasion> occasionSet = Stream.of(occasions).map(Occasion::new).collect(Collectors.toSet());
        descriptor.setOccasions(occasionSet);
        return this;
    }

    public EditReservationDescriptor build() {
        return descriptor;
    }
}
