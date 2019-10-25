package seedu.sugarmummy.logic.parser.food;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FAT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_FOOD_TYPE;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_GI;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_SUGAR;

import java.util.stream.Stream;

import seedu.sugarmummy.logic.commands.food.AddFoodCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.ParserUtil;
import seedu.sugarmummy.logic.parser.Prefix;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.food.Calorie;
import seedu.sugarmummy.model.food.Fat;
import seedu.sugarmummy.model.food.Food;
import seedu.sugarmummy.model.food.FoodName;
import seedu.sugarmummy.model.food.FoodType;
import seedu.sugarmummy.model.food.Gi;
import seedu.sugarmummy.model.food.NutritionValue;
import seedu.sugarmummy.model.food.Sugar;
import seedu.sugarmummy.model.food.exception.FoodNotSuitableException;

/**
 * Parses input arguments and creates a new AddFoodCommand object
 */
public class AddFoodCommandParser implements Parser<AddFoodCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks whether the input food has suitable calorie, gi, sugar, and fat values for type II diabetes patients.
     *
     * @throws FoodNotSuitableException if any of the nutrition value is in dangerous range.
     */
    private void checkValueRange(NutritionValue... values) throws FoodNotSuitableException {
        for (NutritionValue value : values) {
            if (value.isInDangerousRange()) {
                throw new FoodNotSuitableException(value.getWarningMessage());
            }
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddFoodCommand and returns an AddFoodCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFoodCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FOOD, PREFIX_FOOD_TYPE, PREFIX_CALORIE,
                PREFIX_GI, PREFIX_SUGAR, PREFIX_FAT);

        if (!arePrefixesPresent(argMultimap, PREFIX_FOOD, PREFIX_FOOD_TYPE, PREFIX_CALORIE, PREFIX_GI, PREFIX_SUGAR,
                PREFIX_FAT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFoodCommand.MESSAGE_USAGE));
        }

        FoodName name = ParserUtil.parseFoodName(argMultimap.getValue(PREFIX_FOOD).get());
        FoodType foodType = FoodType.getFrom(argMultimap.getValue(PREFIX_FOOD_TYPE).get());

        Calorie calorie = ParserUtil.parseCalorieValue(argMultimap.getValue(PREFIX_CALORIE).get());
        Gi gi = ParserUtil.parseGiValue(argMultimap.getValue(PREFIX_GI).get());
        Sugar sugar = ParserUtil.parseSugarValue(argMultimap.getValue(PREFIX_SUGAR).get());
        Fat fat = ParserUtil.parseFatValue(argMultimap.getValue(PREFIX_FAT).get());

        checkValueRange(calorie, gi, sugar, fat);

        Food newFood = new Food(name, calorie, gi, sugar, fat, foodType);

        return new AddFoodCommand(newFood);
    }

}
