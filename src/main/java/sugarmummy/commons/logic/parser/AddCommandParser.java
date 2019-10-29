package sugarmummy.commons.logic.parser;

import static sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static sugarmummy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_BLOODSUGAR_CONCENTRATION;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_BMI_HEIGHT;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_BMI_WEIGHT;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_DATETIME;
import static sugarmummy.commons.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import sugarmummy.commons.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.Weight;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_RECORDTYPE, PREFIX_DATETIME, PREFIX_BLOODSUGAR_CONCENTRATION,
                PREFIX_BMI_HEIGHT, PREFIX_BMI_WEIGHT);

        if (!arePrefixesPresent(argMultimap, PREFIX_RECORDTYPE, PREFIX_DATETIME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        RecordType rt = ParserUtil.parseRecordType(argMultimap.getValue(PREFIX_RECORDTYPE).get());
        DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());

        switch (rt) {
        case BLOODSUGAR:
            if (!arePrefixesPresent(argMultimap, PREFIX_BLOODSUGAR_CONCENTRATION)
                || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            Concentration concentration = ParserUtil.parseConcentration(
                argMultimap.getValue(PREFIX_BLOODSUGAR_CONCENTRATION).get()
            );
            BloodSugar bloodSugar = new BloodSugar(concentration, dateTime);
            return new AddCommand(bloodSugar);

        case BMI:
            if (!arePrefixesPresent(argMultimap, PREFIX_BMI_HEIGHT, PREFIX_BMI_WEIGHT)
                || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            Height height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_BMI_HEIGHT).get());
            Weight weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_BMI_WEIGHT).get());
            Bmi bmi = new Bmi(height, weight, dateTime);
            return new AddCommand(bmi);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
