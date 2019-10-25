package seedu.sugarmummy.logic.parser.aesthetics;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.ParserUtil.parseColour;

import seedu.sugarmummy.logic.commands.aesthetics.FontColourCommand;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.aesthetics.Colour;

/**
 * Parses input arguments and creates a new FontColourCommand object
 */
public class FontColourCommandParser implements Parser<FontColourCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an EditCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FontColourCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.isEmpty()) {
            return new FontColourCommand();
        }
        Colour fontColour = parseColour(args);
        return new FontColourCommand(fontColour);
    }

}