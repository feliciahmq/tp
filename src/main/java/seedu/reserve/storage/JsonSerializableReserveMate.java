package seedu.reserve.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.reserve.commons.exceptions.IllegalValueException;
import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.reservation.Reservation;

/**
 * An Immutable ReserveMate that is serializable to JSON format.
 */
@JsonRootName(value = "ReserveMate")
class JsonSerializableReserveMate {

    public static final String MESSAGE_DUPLICATE_RESERVATION = "Reservation list contains duplicate reservations(s).";

    private final List<JsonAdaptedReservation> reservations = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableReserveMate} with the given reservations.
     */
    @JsonCreator
    public JsonSerializableReserveMate(@JsonProperty("reservations") List<JsonAdaptedReservation> reservations) {
        this.reservations.addAll(reservations);
    }

    /**
     * Converts a given {@code ReadOnlyReserveMate} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableReserveMate}.
     */
    public JsonSerializableReserveMate(ReadOnlyReserveMate source) {
        reservations.addAll(source.getReservationList().stream().map(JsonAdaptedReservation::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this reservations book into the model's {@code ReserveMate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ReserveMate toModelType() throws IllegalValueException {
        ReserveMate reserveMate = new ReserveMate();
        for (JsonAdaptedReservation jsonAdaptedReservation : reservations) {
            Reservation reservation = jsonAdaptedReservation.toModelType();
            if (reserveMate.hasReservation(reservation)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESERVATION);
            }
            reserveMate.addReservation(reservation);
        }
        return reserveMate;
    }

}
