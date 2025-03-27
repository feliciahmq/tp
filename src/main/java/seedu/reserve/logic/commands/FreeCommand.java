package seedu.reserve.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import seedu.reserve.logic.Messages;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Reservation;

/**
 * Display all free time slots to the user.
 */
public class FreeCommand extends Command {

    public static final String COMMAND_WORD = "free";
    public static final String MESSAGE_NO_FREE_SLOTS = "No available free time slots found.";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final LocalDateTime searchEnd =
            LocalDateTime.now().plusDays(60).minusHours(1).truncatedTo(ChronoUnit.HOURS);

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);

        List<Reservation> reservations = new ArrayList<>(model.getFilteredReservationList());

        if (reservations.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_RESERVATION_LIST);
        }

        StringBuilder message = new StringBuilder("Available free time slots: ");
        LocalDateTime previousEnd = null;
        boolean hasFreeSlots = false;

        for (Reservation reservation : reservations) {
            LocalDateTime currentStart = reservation.getDateTime().value;
            LocalDateTime currentEnd = currentStart.plusHours(1);

            if (previousEnd != null) {
                Duration gap = Duration.between(previousEnd, currentStart);
                if (gap.toHours() >= 1) {
                    hasFreeSlots = true;
                    message.append(formatTimeSlot(previousEnd, currentStart));
                }
            }
            previousEnd = currentEnd;
        }

        if (previousEnd != null && previousEnd.isBefore(searchEnd)) {
            Duration finalGap = Duration.between(previousEnd, searchEnd);
            if (finalGap.toHours() >= 1) {
                hasFreeSlots = true;
                message.append(formatTimeSlot(previousEnd, searchEnd));
            }
        }

        if (!hasFreeSlots) {
            return new CommandResult(MESSAGE_NO_FREE_SLOTS);
        }

        return new CommandResult(message.toString());
    }

    /**
     * Formats a time slot range into a readable string.
     */
    private String formatTimeSlot(LocalDateTime start, LocalDateTime end) {
        return String.format("\n- %s to %s",
                start.format(FORMATTER),
                end.format(FORMATTER));
    }
}
