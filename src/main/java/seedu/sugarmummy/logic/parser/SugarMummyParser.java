package seedu.sugarmummy.logic.parser;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ARGUMENTS_MUST_BE_EMPTY;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.ExitCommand;
import seedu.sugarmummy.logic.commands.HelpCommand;
import seedu.sugarmummy.logic.commands.ListCommand;
import seedu.sugarmummy.logic.commands.achvm.AchvmCommand;
import seedu.sugarmummy.logic.commands.aesthetics.BackgroundCommand;
import seedu.sugarmummy.logic.commands.aesthetics.FontColourCommand;
import seedu.sugarmummy.logic.commands.bio.AddBioCommand;
import seedu.sugarmummy.logic.commands.bio.BioCommand;
import seedu.sugarmummy.logic.commands.bio.ClearBioCommand;
import seedu.sugarmummy.logic.commands.bio.EditBioCommand;
import seedu.sugarmummy.logic.commands.calendar.EventCommand;
import seedu.sugarmummy.logic.commands.food.AddFoodCommand;
import seedu.sugarmummy.logic.commands.food.RecmFoodCommand;
import seedu.sugarmummy.logic.commands.food.ReminderCommand;
import seedu.sugarmummy.logic.commands.records.AddCommand;
import seedu.sugarmummy.logic.commands.records.AverageCommand;
import seedu.sugarmummy.logic.commands.records.DeleteCommand;
import seedu.sugarmummy.logic.parser.aesthetics.BackgroundCommandParser;
import seedu.sugarmummy.logic.parser.aesthetics.FontColourCommandParser;
import seedu.sugarmummy.logic.parser.bio.AddBioCommandParser;
import seedu.sugarmummy.logic.parser.bio.EditBioCommandParser;
import seedu.sugarmummy.logic.parser.calendar.EventCommandParser;
import seedu.sugarmummy.logic.parser.calendar.ReminderCommandParser;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.logic.parser.food.AddFoodCommandParser;
import seedu.sugarmummy.logic.parser.food.RecmFoodCommandParser;
import seedu.sugarmummy.logic.parser.records.AddCommandParser;
import seedu.sugarmummy.logic.parser.records.AverageCommandParser;
import seedu.sugarmummy.logic.parser.records.DeleteCommandParser;
import seedu.sugarmummy.model.food.exception.FoodNotSuitableException;


/**
 * Parses user input.
 */
public class SugarMummyParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, FoodNotSuitableException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord.toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        //        case EditCommand.COMMAND_WORD:
        //            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new HelpCommand();

        case AverageCommand.COMMAND_WORD:
            return new AverageCommandParser().parse(arguments);

        case RecmFoodCommand.COMMAND_WORD:
            return new RecmFoodCommandParser().parse(arguments);

        case AddFoodCommand.COMMAND_WORD:
            return new AddFoodCommandParser().parse(arguments);

        case EventCommand.COMMAND_WORD:
            return new EventCommandParser().parse(arguments);

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommandParser().parse(arguments);

        //=========== User List =============================================================

        case BioCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new BioCommand();

        case AddBioCommand.COMMAND_WORD:
            return new AddBioCommandParser().parse(arguments);

        case EditBioCommand.COMMAND_WORD:
            return new EditBioCommandParser().parse(arguments);

        case ClearBioCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new ClearBioCommand();

        //=========== Achievements =============================================================

        case AchvmCommand.COMMAND_WORD:
            requireEmptyArguments(arguments);
            return new AchvmCommand();

        //=========== Aesthetics =============================================================

        case FontColourCommand.COMMAND_WORD:
            return new FontColourCommandParser().parse(arguments);

        case BackgroundCommand.COMMAND_WORD:
            return new BackgroundCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Ensures commands not meant to have trailing arguments do not have any.
     * Trailing spaces following commands are fine and would have been trimmed off automatically by the program.
     *
     * @param arguments Argument inputs keyed in by the user following the command.
     * @throws ParseException If there are additional arguments after the command.
     */
    private void requireEmptyArguments(String arguments) throws ParseException {
        if (!arguments.isEmpty()) {
            throw new ParseException(MESSAGE_ARGUMENTS_MUST_BE_EMPTY);
        }
    }

}