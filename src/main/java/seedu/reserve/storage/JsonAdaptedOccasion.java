package seedu.reserve.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.reserve.commons.exceptions.IllegalValueException;
import seedu.reserve.model.occasion.Occasion;

/**
 * Jackson-friendly version of {@link Occasion}.
 */
class JsonAdaptedOccasion {

    private final String occasionName;

    /**
     * Constructs a {@code JsonAdaptedOccasion} with the given {@code occasionName}.
     */
    @JsonCreator
    public JsonAdaptedOccasion(String occasionName) {
        this.occasionName = occasionName;
    }

    /**
     * Converts a given {@code Occasion} into this class for Jackson use.
     */
    public JsonAdaptedOccasion(Occasion source) {
        occasionName = source.occasionName;
    }

    @JsonValue
    public String getOccasionName() {
        return occasionName;
    }

    /**
     * Converts this Jackson-friendly adapted occasion object into the model's {@code Occasion} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted occasion.
     */
    public Occasion toModelType() throws IllegalValueException {
        if (!Occasion.isValidOccasionName(occasionName)) {
            throw new IllegalValueException(Occasion.MESSAGE_OCCASION_CONSTRAINTS);
        }
        return new Occasion(occasionName);
    }

}
