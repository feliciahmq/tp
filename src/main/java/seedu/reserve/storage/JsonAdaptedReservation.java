package seedu.reserve.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.reserve.commons.exceptions.IllegalValueException;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;
import seedu.reserve.model.reservation.Reservation;

/**
 * Jackson-friendly version of {@link Reservation}.
 */
class JsonAdaptedReservation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reservation's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String diners;
    private final String dateTime;
    private final List<JsonAdaptedOccasion> occasions = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedReservation} with the given reservation details.
     */
    @JsonCreator
    public JsonAdaptedReservation(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                  @JsonProperty("email") String email, @JsonProperty("diners") String diners,
                                  @JsonProperty("dateTime") String dateTime,
                                  @JsonProperty("tags") List<JsonAdaptedOccasion> occasions) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.diners = diners;
        this.dateTime = dateTime;
        if (occasions != null) {
            this.occasions.addAll(occasions);
        }
    }

    /**
     * Converts a given {@code Reservation} into this class for Jackson use.
     */
    public JsonAdaptedReservation(Reservation source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        diners = source.getDiners().toString();
        dateTime = source.getDateTime().toString();
        occasions.addAll(source.getTags().stream()
                .map(JsonAdaptedOccasion::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted reservation object into the model's {@code Reservation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reservation.
     */
    public Reservation toModelType() throws IllegalValueException {
        final List<Occasion> reservationOccasions = new ArrayList<>();
        for (JsonAdaptedOccasion tag : occasions) {
            reservationOccasions.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (diners == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Diners.class.getSimpleName()));
        }

        if (!Diners.isValidDiners(diners)) {
            throw new IllegalValueException(Diners.MESSAGE_CONSTRAINTS);
        }
        final Diners modelDiners = new Diners(diners);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DateTime.class.getSimpleName()));
        }

        if (!DateTime.isValidFileInputDateTime(dateTime)) {
            throw new IllegalValueException(DateTime.MESSAGE_CONSTRAINTS);
        }
        final DateTime modelDateTime = DateTime.fromFileString(dateTime);

        final Set<Occasion> modelOccasions = new HashSet<>(reservationOccasions);
        return new Reservation(modelName, modelPhone, modelEmail, modelDiners, modelDateTime, modelOccasions);
    }

}
