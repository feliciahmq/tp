package seedu.reserve.logic.parser;

import static seedu.reserve.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_NUMBER_OF_DINERS;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.reserve.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.reserve.logic.commands.AddCommand;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.customer.Address;
import seedu.reserve.model.customer.Customer;
import seedu.reserve.model.customer.DateTime;
import seedu.reserve.model.customer.Diners;
import seedu.reserve.model.customer.Email;
import seedu.reserve.model.customer.Name;
import seedu.reserve.model.customer.Phone;
import seedu.reserve.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_NUMBER_OF_DINERS, PREFIX_DATE_TIME, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_NUMBER_OF_DINERS, PREFIX_DATE_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_NUMBER_OF_DINERS, PREFIX_DATE_TIME);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Diners diners = ParserUtil.parseDiners(argMultimap.getValue(PREFIX_NUMBER_OF_DINERS).get());
        DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE_TIME).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Customer customer = new Customer(name, phone, email, address, diners, dateTime, tagList);

        return new AddCommand(customer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
