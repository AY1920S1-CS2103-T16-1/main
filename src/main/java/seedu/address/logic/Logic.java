package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.bio.User;
import seedu.address.model.person.Person;
import seedu.address.ui.DisplayPaneType;
import seedu.sgm.model.food.Food;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the {@code DisplayPaneType} for updating the main pane based on different commands.
     */
    DisplayPaneType getDisplayPaneType();

    /**
     * Returns a boolean indicating whether a new pane is to be created, regardless of whether an existing one already
     * exists.
     */
    boolean getNewPaneToBeCreated();

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the a list of foods.
     *
     * @see seedu.address.model.Model#getFoodList()
     */
    ObservableList<Food> getFoodList();

    /**
     * Returns an unmodifiable view of the filtered list of foods
     */
    ObservableList<Food> getFilterFoodList();
    
    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //=========== User List =============================================================

    /**
     * Returns the user prefs' address book file path.
     */
    Path getUserListFilePath();

    /**
     * Returns the UserList.
     *
     * @see seedu.address.model.Model#getUserList()
     */
    ReadOnlyUserList getUserList();

    /**
     * Returns an unmodifiable view of the filtered list of users
     */
    ObservableList<User> getFilteredUserList();
    

}
