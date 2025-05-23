package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DINERS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.commons.util.CollectionUtil;
import seedu.reserve.commons.util.ToStringBuilder;
import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Preference;
import seedu.reserve.model.reservation.Reservation;

/**
 * Edits the details of an existing reservation in the reservation book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reservation identified "
            + "by the index number used in the displayed reservation list. "
            + "Existing values will be overwritten by the input values.\n\n"
            + "Parameters:\n"
            + "- " + "INDEX (must be a positive integer)\n"
            + "- " + "[" + PREFIX_NAME + "NAME]\n"
            + "- " + "[" + PREFIX_PHONE + "PHONE]\n"
            + "- " + "[" + PREFIX_EMAIL + "EMAIL]\n"
            + "- " + "[" + PREFIX_NUMBER_OF_DINERS + "NUMBER OF DINERS]\n"
            + "- " + "[" + PREFIX_DATE_TIME + "DATETIME]\n"
            + "- " + "[" + PREFIX_OCCASION + "OCCASION]...\n\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_NUMBER_OF_DINERS + "5 "
            + PREFIX_DATE_TIME + "2025-04-28 1800 ";

    public static final String MESSAGE_EDIT_RESERVATION_SUCCESS = "Edited Reservation:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_EDIT_RESERVATION_WARNING = "Warning: You modified a past reservation!\n";
    public static final String MESSAGE_CANNOT_CHANGE_FUTURE_TO_PAST =
            "You cannot change a future reservation date to a past date.\n";

    private final Index index;
    private final EditReservationDescriptor editReservationDescriptor;

    /**
     * @param index of the reservation in the filtered reservation list to edit
     * @param editReservationDescriptor details to edit the reservation with
     */
    public EditCommand(Index index, EditReservationDescriptor editReservationDescriptor) {
        requireNonNull(index);
        requireNonNull(editReservationDescriptor);

        this.index = index;
        this.editReservationDescriptor = new EditReservationDescriptor(editReservationDescriptor);
    }

    /**
     * Checks if the DateTime is before the current time.
     *
     * @param dateTime The DateTime to check.
     * @return true if the DateTime is before the current time, false otherwise.
     */
    private boolean isDateTimeBeforeCurrentTime(DateTime dateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return dateTime.value.isBefore(currentDateTime);
    }
    private boolean isDuplicateReservation(Model model, int selfIndex, Reservation edited) {
        List<Reservation> reservations = model.getFilteredReservationList();

        return reservations.stream()
                .filter(existing -> reservations.indexOf(existing) != selfIndex)
                .anyMatch(existing -> existing.getDateTime().equals(edited.getDateTime())
                        && (existing.getEmail().equals(edited.getEmail())
                        || existing.getPhone().equals(edited.getPhone())));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String out = "";
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToEdit = lastShownList.get(index.getZeroBased());
        Reservation editedReservation = createEditedReservation(reservationToEdit, editReservationDescriptor);

        if (isDateTimeBeforeCurrentTime(reservationToEdit.getDateTime())) {
            out = MESSAGE_EDIT_RESERVATION_WARNING;
        } else {
            if (!DateTime.isValidDateTime(editedReservation.getDateTime().toString())
                    && isDateTimeBeforeCurrentTime(editedReservation.getDateTime())) {
                throw new CommandException(MESSAGE_CANNOT_CHANGE_FUTURE_TO_PAST + DateTime.MESSAGE_CONSTRAINTS);
            } else if (!DateTime.isValidDateTime(editedReservation.getDateTime().toString())) {
                throw new CommandException(DateTime.MESSAGE_CONSTRAINTS);
            }
        }

        if (isDuplicateReservation(model, index.getZeroBased(), editedReservation)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_RESERVATION);
        }

        model.setReservation(reservationToEdit, editedReservation);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        return new CommandResult(String.format(out
                + MESSAGE_EDIT_RESERVATION_SUCCESS, Messages.format(editedReservation)));

    }



    /**
     * Creates and returns a {@code Reservation} with the details of {@code reservationToEdit}
     * edited with {@code editReservationDescriptor}.
     */
    private static Reservation createEditedReservation(Reservation reservationToEdit,
                                                       EditReservationDescriptor editReservationDescriptor) {
        assert reservationToEdit != null;

        Name updatedName = editReservationDescriptor.getName().orElse(reservationToEdit.getName());
        Phone updatedPhone = editReservationDescriptor.getPhone().orElse(reservationToEdit.getPhone());
        Email updatedEmail = editReservationDescriptor.getEmail().orElse(reservationToEdit.getEmail());
        Diners updateDiners = editReservationDescriptor.getDiners().orElse(reservationToEdit.getDiners());
        DateTime updateDateTime = editReservationDescriptor.getDateTime().orElse(reservationToEdit.getDateTime());
        Set<Occasion> updatedOccasions = editReservationDescriptor
            .getOccasions().orElse(reservationToEdit.getOccasions());
        Preference updatedPreference = editReservationDescriptor
            .getPreference().orElse(reservationToEdit.getPreference());
        return new Reservation(updatedName, updatedPhone, updatedEmail,
                updateDiners, updateDateTime, updatedOccasions, updatedPreference);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editReservationDescriptor.equals(otherEditCommand.editReservationDescriptor);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editReservationDescriptor", editReservationDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the reservation with. Each non-empty field value will replace the
     * corresponding field value of the reservation.
     */
    public static class EditReservationDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Diners diners;
        private DateTime dateTime;
        private Set<Occasion> occasions;
        private Preference preference;

        public EditReservationDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code occasions} is used internally.
         */
        public EditReservationDescriptor(EditReservationDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setDiners(toCopy.diners);
            setDateTime(toCopy.dateTime);
            setOccasions(toCopy.occasions);
            setPreference(toCopy.preference);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, diners, dateTime, occasions);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setDiners(Diners diners) {
            this.diners = diners;
        }

        public Optional<Diners> getDiners() {
            return Optional.ofNullable(diners);
        }

        public void setDateTime(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<DateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        /**
         * Sets {@code occasions} to this object's {@code occasions}.
         * A defensive copy of {@code occasions} is used internally.
         */
        public void setOccasions(Set<Occasion> occasions) {
            this.occasions = (occasions != null) ? new HashSet<>(occasions) : null;
        }

        public void setPreference(Preference preference) {
            this.preference = preference;
        }

        public Optional<Preference> getPreference() {
            return Optional.ofNullable(preference);
        }

        /**
         * Returns an unmodifiable occasion set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code occasions} is null.
         */
        public Optional<Set<Occasion>> getOccasions() {
            return (occasions != null) ? Optional.of(Collections.unmodifiableSet(occasions)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReservationDescriptor)) {
                return false;
            }

            EditReservationDescriptor otherEditReservationDescriptor = (EditReservationDescriptor) other;
            return Objects.equals(name, otherEditReservationDescriptor.name)
                    && Objects.equals(phone, otherEditReservationDescriptor.phone)
                    && Objects.equals(email, otherEditReservationDescriptor.email)
                    && Objects.equals(diners, otherEditReservationDescriptor.diners)
                    && Objects.equals(dateTime, otherEditReservationDescriptor.dateTime)
                    && Objects.equals(occasions, otherEditReservationDescriptor.occasions)
                    && Objects.equals(preference, otherEditReservationDescriptor.preference);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("diners", diners)
                    .add("dateTime", dateTime)
                    .add("occasions", occasions)
                    .add("preferences", preference)
                    .toString();
        }
    }
}
