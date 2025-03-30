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
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedOccasion(String occasionName) {
        this.occasionName = occasionName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedOccasion(Occasion source) {
        occasionName = source.occasionName;
    }

    @JsonValue
    public String getOccasionName() {
        return occasionName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Occasion toModelType() throws IllegalValueException {
        if (!Occasion.isValidTagName(occasionName)) {
            throw new IllegalValueException(Occasion.MESSAGE_CONSTRAINTS);
        }
        return new Occasion(occasionName);
    }

}
