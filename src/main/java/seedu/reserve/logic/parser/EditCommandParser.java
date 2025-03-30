package seedu.reserve.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DINERS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_OCCASION;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.reserve.commons.core.index.Index;
import seedu.reserve.commons.util.StringUtil;
import seedu.reserve.logic.commands.EditCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.occasion.Occasion;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE_TIME,
                        PREFIX_NUMBER_OF_DINERS, PREFIX_OCCASION);

        Index index;

        try {
            String parse = argMultimap.getPreamble();

            if (!StringUtil.isInteger(parse)) {
                throw new NumberFormatException();
            }
            index = ParserUtil.parseIndex(parse);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX, pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_DATE_TIME, PREFIX_NUMBER_OF_DINERS);

        EditCommand.EditReservationDescriptor editReservationDescriptor = new EditCommand.EditReservationDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editReservationDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editReservationDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editReservationDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }

        if (argMultimap.getValue(PREFIX_NUMBER_OF_DINERS).isPresent()) {
            editReservationDescriptor.setDiners(ParserUtil.parseDiners(argMultimap.getValue(PREFIX_NUMBER_OF_DINERS)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_DATE_TIME).isPresent()) {
            editReservationDescriptor
                    .setDateTime(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE_TIME).get()));
        }
        parseOccasionsForEdit(argMultimap.getAllValues(PREFIX_OCCASION))
            .ifPresent(editReservationDescriptor::setOccasions);

        if (!editReservationDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editReservationDescriptor);
    }

    /**
     * Parses {@code Collection<String> occasions} into a {@code Set<Occasion>} if {@code occasions} is non-empty.
     * If {@code occasions} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Occasion>} containing zero occasions.
     */
    private Optional<Set<Occasion>> parseOccasionsForEdit(Collection<String> occasions) throws ParseException {
        assert occasions != null;

        if (occasions.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> occasionSet =
            occasions.size() == 1 && occasions.contains("") ? Collections.emptySet() : occasions;
        return Optional.of(ParserUtil.parseOccasions(occasionSet));
    }

}
