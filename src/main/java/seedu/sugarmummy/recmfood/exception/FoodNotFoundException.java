package seedu.sugarmummy.recmfood.exception;

/**
 * Signals that the operation is unable to find the specified food.
 */
public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException() {
        super("Cannot find the specified food!");
    }
}
