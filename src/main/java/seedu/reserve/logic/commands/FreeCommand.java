package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Reservation;
import seedu.reserve.model.reservation.ReservationBetweenDatePredicate;

/**
 * Display all free time slots to the user.
 */
public class FreeCommand extends Command {

    public static final String COMMAND_WORD = "free";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Find all free time slots in a given day\n\n"
            + "Parameters: " + PREFIX_DATE_TIME + "DATE\n\n"
            + "Example: " + COMMAND_WORD + " d/2025-05-01";
    public static final String MESSAGE_NO_FREE_SLOTS = "No available free time slots found.";
    public static final String MESSAGE_ALL_FREE_SLOTS = "All timings are available on this date.";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private final ReservationBetweenDatePredicate predicate;
    private final LocalDateTime searchStart;
    private final LocalDateTime searchEnd;

    /**
     * Constructs a FreeCommand for the specified date/time.
     *
     * @param date The starting date/time to search for free slots
     */
    public FreeCommand(DateTime date) {
        this.searchStart = date.value;
        this.searchEnd = date.value.plusDays(1);
        this.predicate = new ReservationBetweenDatePredicate(date,
                new DateTime(this.searchEnd.format(FORMATTER)));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredReservationList(predicate);

        List<Reservation> reservations = new ArrayList<>(model.getFilteredReservationList());
        if (reservations.isEmpty()) {
            model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
            return new CommandResult(MESSAGE_ALL_FREE_SLOTS);
        }

        List<TimeSlot> freeSlots = getAllFreeSlots(reservations);
        if (freeSlots.isEmpty()) {
            model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
            return new CommandResult(MESSAGE_NO_FREE_SLOTS);
        }

        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);

        return new CommandResult(formatFreeSlots(freeSlots));
    }

    /**
     * Calculates all available free time slots between reservations.
     *
     * @param reservations The list of reservations to check against
     * @return List of available time slots (each at least 1 hour long)
     */
    private List<TimeSlot> getAllFreeSlots(List<Reservation> reservations) {
        List<TimeSlot> freeSlots = new ArrayList<>();

        // Check slot before first reservation
        LocalDateTime firstReservationStart = reservations.get(0).getDateTime().value;
        addSlotIfValid(freeSlots, searchStart, firstReservationStart);

        // Check slots between reservations
        LocalDateTime previousEnd = firstReservationStart.plusHours(1);
        for (int i = 1; i < reservations.size(); i++) {
            LocalDateTime currentStart = reservations.get(i).getDateTime().value;
            addSlotIfValid(freeSlots, previousEnd, currentStart);
            previousEnd = currentStart.plusHours(1);
        }

        // Check slot after last reservation
        addSlotIfValid(freeSlots, previousEnd, searchEnd);

        return freeSlots;
    }

    /**
     * Adds a time slot to the list if it meets minimum duration requirements.
     *
     * @param slots The list to add valid slots to
     * @param start The proposed start time
     * @param end The proposed end time
     */
    private void addSlotIfValid(List<TimeSlot> slots, LocalDateTime start, LocalDateTime end) {
        if (start.isBefore(end) && Duration.between(start, end).toHours() >= 1) {
            slots.add(new TimeSlot(start, end));
        }
    }

    /**
     * Formats the list of free slots into a user-friendly message.
     *
     * @param freeSlots The slots to format
     * @return Formatted string showing all available time slots
     */
    private String formatFreeSlots(List<TimeSlot> freeSlots) {
        StringBuilder message = new StringBuilder("Available free time slots:");
        freeSlots.forEach(message::append);
        return message.toString();
    }

    /**
     * Helper record to represent a time slot with start and end times.
     *
     * @param start The start time of the slot
     * @param end The end time of the slot
     */
    private record TimeSlot(LocalDateTime start, LocalDateTime end) {

        @Override
        public String toString() {
            return String.format("\n- %s to %s",
                    start.format(FORMATTER),
                    end.format(FORMATTER));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FreeCommand)) {
            return false;
        }

        FreeCommand otherFreeCommand = (FreeCommand) other;
        return this.predicate.equals(otherFreeCommand.predicate);
    }
}
