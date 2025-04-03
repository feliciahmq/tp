package seedu.reserve.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.reserve.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.reserve.testutil.Assert.assertThrows;
import static seedu.reserve.testutil.TypicalIndexes.INDEX_FIRST_RESERVATION;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.reserve.commons.core.index.Index;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.occasion.Occasion;
import seedu.reserve.model.reservation.DateTime;
import seedu.reserve.model.reservation.Diners;
import seedu.reserve.model.reservation.Email;
import seedu.reserve.model.reservation.Name;
import seedu.reserve.model.reservation.Phone;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_OCCASION = "#gambling";
    private static final String INVALID_OCCASION_LONG = "long word".repeat(50);
    private static final String INVALID_OCCASION_SHORT = "a";
    private static final String INVALID_DINERS = "0";
    private static final String INVALID_DATETIME = "2030-04-12 180";
    private static final String INVALID_FILTER_DATETIME = "2025-13-01 1900";
    private static final String INVALID_DATETIME_NON_HOURLY = "2025-04-03 1430";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "98765432";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_DINERS = "2";
    private static final String VALID_DATETIME = "2025-04-12 1800";
    private static final String VALID_FILTER_DATETIME = "2025-01-01 1400";
    private static final String VALID_OCCASION_1 = "Birthday";
    private static final String VALID_OCCASION_2 = "Anniversary";
    private static final String VALID_OCCASION_SYMBOL = "pre-wedding!(b4 wedding) - ' . , & ! ( ) /.";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_RESERVATION, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_RESERVATION, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseDiners_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDiners((String) null));
    }

    @Test
    public void parseDiners_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDiners(INVALID_DINERS));
    }

    @Test
    public void parseDiners_validValueWithoutWhitespace_returnsDiners() throws Exception {
        Diners expectedDiners = new Diners(VALID_DINERS);
        assertEquals(expectedDiners, ParserUtil.parseDiners(VALID_DINERS));
    }

    @Test
    public void parseDiners_validValueWithWhitespace_returnsTrimmedDiners() throws Exception {
        String dinersWithWhitespace = WHITESPACE + VALID_DINERS + WHITESPACE;
        Diners expectedDiners = new Diners(VALID_DINERS);
        assertEquals(expectedDiners, ParserUtil.parseDiners(dinersWithWhitespace));
    }

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime((String) null));
    }

    @Test
    public void parseDateTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime(INVALID_DATETIME));
    }

    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsDateTime() throws Exception {
        DateTime expectedDateTime = new DateTime(VALID_DATETIME);
        assertEquals(expectedDateTime, ParserUtil.parseDateTime(VALID_DATETIME));
    }

    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedDateTime() throws Exception {
        String dateTimeWithWhitespace = WHITESPACE + VALID_DATETIME + WHITESPACE;
        DateTime expectedDateTime = new DateTime(VALID_DATETIME);
        assertEquals(expectedDateTime, ParserUtil.parseDateTime(dateTimeWithWhitespace));
    }

    @Test
    public void parseDateTimeFilter_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTimeFilter(null));
    }

    @Test
    public void parseDateTimeFilter_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTimeFilter(INVALID_FILTER_DATETIME));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTimeFilter(INVALID_DATETIME));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTimeFilter(INVALID_DATETIME_NON_HOURLY));
    }

    @Test
    public void parseDateTimeFilter_validValue_returnsDateTime() throws Exception {
        DateTime expectedDateTime = DateTime.fromFileString(VALID_FILTER_DATETIME);
        assertEquals(expectedDateTime, ParserUtil.parseDateTimeFilter(VALID_FILTER_DATETIME));
        DateTime expectedDateTimeAfter = new DateTime(VALID_DATETIME);
        assertEquals(expectedDateTimeAfter, ParserUtil.parseDateTimeFilter(VALID_DATETIME));
    }

    @Test
    public void parseDateTimeFilter_validValueWithWhitespace_returnsTrimmedDateTime() throws Exception {
        String dateTimeWithWhitespace = WHITESPACE + VALID_FILTER_DATETIME + WHITESPACE;
        DateTime expectedDateTime = ParserUtil.parseDateTimeFilter(dateTimeWithWhitespace);
        assertEquals(expectedDateTime, ParserUtil.parseDateTimeFilter(dateTimeWithWhitespace));
    }

    @Test
    public void parseOccasion_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOccasion(null));
    }

    @Test
    public void parseOccasion_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOccasion(INVALID_OCCASION));
        assertThrows(ParseException.class, () -> ParserUtil.parseOccasion(INVALID_OCCASION_LONG));
        assertThrows(ParseException.class, () -> ParserUtil.parseOccasion(INVALID_OCCASION_SHORT));
    }

    @Test
    public void parseOccasion_validValueWithoutWhitespace_returnsOccasion() throws Exception {
        Occasion expectedOccasion = new Occasion(VALID_OCCASION_1);
        assertEquals(expectedOccasion, ParserUtil.parseOccasion(VALID_OCCASION_1));
    }

    @Test
    public void parseOccasion_validValueWithSymbols_returnsOccasion() throws Exception {
        Occasion expectedOccasion = new Occasion(VALID_OCCASION_SYMBOL);
        assertEquals(expectedOccasion, ParserUtil.parseOccasion(VALID_OCCASION_SYMBOL));
    }

    @Test
    public void parseOccasion_validValueWithWhitespace_returnsTrimmedOccasion() throws Exception {
        String occasionWithWhitespace = WHITESPACE + VALID_OCCASION_1 + WHITESPACE;
        Occasion expectedOccasion = new Occasion(VALID_OCCASION_1);
        assertEquals(expectedOccasion, ParserUtil.parseOccasion(occasionWithWhitespace));
    }

    @Test
    public void parseOccasions_collectionWithInvalidOccasions_throwsParseException() {
        assertThrows(ParseException.class, () ->
            ParserUtil.parseOccasions(Arrays.asList(VALID_OCCASION_1, INVALID_OCCASION)));
        assertThrows(ParseException.class, () ->
                ParserUtil.parseOccasions(Arrays.asList(VALID_OCCASION_1, INVALID_OCCASION_LONG)));
    }

    @Test
    public void parseOccasions_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseOccasions(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseOccasions_collectionWithValidOccasions_returnsOccasionSet() throws Exception {
        Set<Occasion> actualOccasionSet = ParserUtil.parseOccasions(Arrays.asList(VALID_OCCASION_1, VALID_OCCASION_2));
        Set<Occasion> expectedOccasionSet = new HashSet<Occasion>(Arrays
            .asList(new Occasion(VALID_OCCASION_1), new Occasion(VALID_OCCASION_2)));

        assertEquals(expectedOccasionSet, actualOccasionSet);
    }

    @Test
    public void parseDeleteArgs_validInputWithConfirmation_success() throws ParseException {
        String input = "1 cfm";
        Pair<Index, Boolean> result = ParserUtil.parseDeleteArgs(input);
        assertEquals(Index.fromOneBased(1), result.getKey()); // index 1
        assertTrue(result.getValue()); // confirmed is true
    }

    @Test
    public void parseDeleteArgs_validInputWithSpacesWithConfirmation_success() throws ParseException {
        String input = "1             cfm";
        Pair<Index, Boolean> result = ParserUtil.parseDeleteArgs(input);
        assertEquals(Index.fromOneBased(1), result.getKey()); // index 1
        assertTrue(result.getValue()); // confirmed is true
    }

    @Test
    public void parseDeleteArgs_validInputWithMoreSpacesWithConfirmation_success() throws ParseException {
        String input = "1             cfm      ";
        Pair<Index, Boolean> result = ParserUtil.parseDeleteArgs(input);
        assertEquals(Index.fromOneBased(1), result.getKey()); // index 1
        assertTrue(result.getValue()); // confirmed is true
    }
}
