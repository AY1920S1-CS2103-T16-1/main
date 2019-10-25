package seedu.sugarmummy.logic;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.bio.ReadOnlyUserList;
import seedu.sugarmummy.model.bio.User;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.ReadOnlyCalendar;
import seedu.sugarmummy.model.food.Food;
import seedu.sugarmummy.model.record.Record;
import seedu.sugarmummy.ui.DisplayPaneType;


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
    boolean getnewPaneIsToBeCreated();

    /**
     * Returns the a list of foods.
     *
     * @see Model#getFoodList()
     */
    ObservableList<Food> getFoodList();

    /**
     * Returns an unmodifiable view of the filtered list of foods
     */
    ObservableList<Food> getFilterFoodList();

    public ObservableList<Record> getRecordList();

    public ObservableList<Record> getFilterRecordList();

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
     * @see Model#getUserList()
     */
    ReadOnlyUserList getUserList();

    /**
     * Returns an unmodifiable view of the filtered list of users
     */
    ObservableList<User> getFilteredUserList();

    /**
     * Return a list of maps of fields in the json file that contain invalid references.
     *
     * @return List of maps of fields in the json file containing invalid references.
     */
    List<Map<String, String>> getListOfFieldsContainingInvalidReferences();

    //=========================Calendar==============================

    /**
     * Returns the Calendar.
     *
     * @see Model#getCalendar()
     */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns an unmodifiable view of the filtered list of calendar entries
     */
    ObservableList<CalendarEntry> getFilteredCalendarEntryList();

    /**
     * Returns an unmodifiable view of past reminders.
     */
    ObservableList<CalendarEntry> getPastReminderList();

    /**
     * Reschedule upcoming reminders.
     */
    void schedule();

    /**
     * Stop all upcoming reminders;
     */
    void stopAllReminders();

    //=========== Statistics List =============================================================

    /**
     * Returns the last average type calculated.
     */
    SimpleStringProperty getAverageType();

    /**
     * Returns the last record type whose average is calculated.
     */
    SimpleStringProperty getRecordType();

    /**
     * Returns a {@code Map} object that maps time period to the respective average values.
     */
    ObservableMap<LocalDate, Double> getAverageMap();

    //=========== Aesthetics =============================================================

    /**
     * Returns the font colour to be set for this app.
     */
    Colour getFontColour();

    /**
     * Returns the background to be set for this app.
     */
    Background getBackground();

}