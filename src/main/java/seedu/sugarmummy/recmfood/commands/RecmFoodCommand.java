package seedu.sugarmummy.recmfood.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.recmfood.model.Food;
import seedu.sugarmummy.recmfood.model.FoodComparator;
import seedu.sugarmummy.recmfood.predicates.FoodTypeIsWantedPredicate;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Recommends suitable food or meals for diabetic patients.
 */
public class RecmFoodCommand extends Command {

    public static final String COMMAND_WORD = "recmf";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gets a list of food recommendations."
            + "Recommendations can be filtered by keywords and flags:\n"
            + "-nsv: breakfast recommendations\n"
            + "-sv: lunch recommendations\n"
            + "-f: fruit recommendations\n"
            + "-p: protein recommendations\n"
            + "-s: snack recommendations\n"
            + "-m: meal recommendations\n"
            + "Usage:" + COMMAND_WORD + "[-FLAG]... [fn/FOOD_NAME]";

    private static final String MESSAGE_RESPONSE_EMPTY_FOOD_LIST = "There is no match in the current database :( "
            + "Try adding more new foods or reducing some filters~";
    private static final String MESSAGE_RESPONSE_NORMAL_LIST = "Hope you like what I've found for you~";

    private final FoodTypeIsWantedPredicate typePredicate;
    private final Predicate<Food> namePredicate;
    private final FoodComparator foodComparator;

    public RecmFoodCommand(FoodTypeIsWantedPredicate typePredicate, Predicate<Food> foodNamePredicate,
                           FoodComparator foodComparator) {
        this.typePredicate = typePredicate;
        this.namePredicate = foodNamePredicate;
        this.foodComparator = foodComparator;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredFoodList(food -> typePredicate.test(food) && namePredicate.test(food));
        model.sortFoodListInAscendingOrder(foodComparator);
        if (model.getFilterFoodList().size() == 0) {
            return new CommandResult(MESSAGE_RESPONSE_EMPTY_FOOD_LIST);
        }
        return new CommandResult(MESSAGE_RESPONSE_NORMAL_LIST);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.RECM_FOOD;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RecmFoodCommand)) {
            return false;
        }
        return typePredicate.equals(((RecmFoodCommand) other).typePredicate)
                && namePredicate.equals(namePredicate);
    }
}
