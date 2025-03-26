package seedu.reserve.model.reservation;

import java.util.function.Predicate;

import seedu.reserve.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Reservation}'s {@code DateTime} is between a given start and end {@code DateTime}
 */
public class ReservationBetweenDatePredicate implements Predicate<Reservation> {
    private final DateTime startDate;
    private final DateTime endDate;

    /**
     * Constructor to create predicate given the start and end date
     */
    public ReservationBetweenDatePredicate(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Reservation reservation) {
        DateTime reservationDateTime = reservation.getDateTime();
        return reservationDateTime.isBetween(startDate, endDate);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReservationBetweenDatePredicate)) {
            return false;
        }

        ReservationBetweenDatePredicate compare = (ReservationBetweenDatePredicate) other;
        if ((this.startDate.equals(compare.startDate)) && (this.endDate.equals(compare.endDate))) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("startDate", this.startDate)
                .add("endDate", this.endDate).toString();
    }
}
