package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddFoodCommand;
import seedu.address.logic.commands.AverageCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RecmFoodCommand;
import seedu.address.logic.commands.achvm.AchvmCommand;
import seedu.address.logic.commands.bio.AddBioCommand;
import seedu.address.logic.commands.bio.BioCommand;
import seedu.address.logic.commands.bio.ClearBioCommand;
import seedu.address.logic.commands.bio.EditBioCommand;
import seedu.address.logic.parser.bio.AddBioCommandParser;
import seedu.address.logic.parser.bio.EditBioCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

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
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord.toLowerCase()) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case BioCommand.COMMAND_WORD:
            return new BioCommand();

        case AddBioCommand.COMMAND_WORD:
            return new AddBioCommandParser().parse(arguments);

        case EditBioCommand.COMMAND_WORD:
            return new EditBioCommandParser().parse(arguments);

        case ClearBioCommand.COMMAND_WORD:
            return new ClearBioCommand();

        case AchvmCommand.COMMAND_WORD:
            return new AchvmCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AverageCommand.COMMAND_WORD:
            return new AverageCommandParser().parse(arguments);

        case RecmFoodCommand.COMMAND_WORD:
            return new RecmFoodCommandParser().parse(arguments);

        case AddFoodCommand.COMMAND_WORD:
            return new AddFoodCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
