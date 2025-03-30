package seedu.reserve.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.reserve.commons.exceptions.IllegalValueException;
import seedu.reserve.model.tag.Occasion;

/**
 * Jackson-friendly version of {@link Occasion}.
 */
class JsonAdaptedTag {

    private final String tagName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Occasion source) {
        tagName = source.occasionName;
    }

    @JsonValue
    public String getTagName() {
        return tagName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Occasion toModelType() throws IllegalValueException {
        if (!Occasion.isValidTagName(tagName)) {
            throw new IllegalValueException(Occasion.MESSAGE_CONSTRAINTS);
        }
        return new Occasion(tagName);
    }

}
