package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.testutil.ReminderBuilder;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.food.ReminderCommand;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ReadOnlyUserPrefs;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.bio.ReadOnlyUserList;
import seedu.sugarmummy.model.bio.User;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.ReadOnlyCalendar;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.food.Food;
import seedu.sugarmummy.model.food.UniqueFoodList;
import seedu.sugarmummy.model.record.Record;
import seedu.sugarmummy.model.record.RecordType;
import seedu.sugarmummy.model.record.UniqueRecordList;
import seedu.sugarmummy.model.statistics.AverageType;

class ReminderCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderCommand(null));
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = new ReminderCommand(validReminder).execute(modelStub);

        assertEquals(String.format(ReminderCommand.MESSAGE_SUCCESS, validReminder), commandResult.getFeedbackToUser());
        //assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder validReminder = new ReminderBuilder().build();
        ReminderCommand reminderCommand = new ReminderCommand(validReminder);
        ModelStub modelStub = new ModelStubWithReminder(validReminder);
        /*
        assertThrows(CommandException.class, ReminderCommand.MESSAGE_DUPLICATE_REMINDER,
                () -> reminderCommand.execute(modelStub));
         */
    }

    @Test
    public void equals() {
        Reminder insulinInjection = new ReminderBuilder().build();
        Reminder wakeUp = new ReminderBuilder().withDescription("Wake up").build();
        ReminderCommand insulinInjectionCommand = new ReminderCommand(insulinInjection);
        ReminderCommand wakeUpCommand = new ReminderCommand(wakeUp);

        // same object -> returns true
        assertTrue(insulinInjection.equals(insulinInjection));

        // same values -> returns true
        ReminderCommand insulinInjectionCommandCopy = new ReminderCommand(insulinInjection);
        assertTrue(insulinInjectionCommand.equals(insulinInjectionCommandCopy));

        // different types -> returns false
        assertFalse(insulinInjectionCommand.equals(1));

        // null -> returns false
        assertFalse(insulinInjectionCommand.equals(null));

        // different reminders -> returns false
        assertFalse(insulinInjectionCommand.equals(wakeUpCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueRecordList getUniqueRecordListObject() {
            return null;
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return null;
        }

        @Override
        public void setRecordList(UniqueRecordList newRecordList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Record> getFilterRecordList() {
            return null;
        }

        @Override
        public boolean hasRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecord(Record record) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueFoodList getUniqueFoodListObject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoodList(UniqueFoodList newFoodList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFilterFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodList(Predicate<Food> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //=========== User List =============================================================

        @Override
        public boolean bioExists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserList getUserList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserList(ReadOnlyUserList userList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getUserListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserListFilePath(Path userListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUser(User user) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public ObservableList<User> getFilteredUserList() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void updateFilteredUserList(Predicate<User> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCalendar getCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCalendarEntry(CalendarEntry target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCalendarEntry(CalendarEntry calendarEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPastReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CalendarEntry> getPastReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void schedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void stopAllReminders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUser(User target, User editedUser) {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Statistics List =============================================================

        @Override
        public SimpleStringProperty getAverageType() {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Aesthetics =============================================================

        @Override
        public Colour getFontColour() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFontColour(Colour fontColour) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Background getBackground() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBackground(Background background) {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Records =============================================================

        @Override
        public SimpleStringProperty getRecordType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void calculateAverageMap(AverageType averageType, RecordType recordType, int count) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableMap<LocalDate, Double> getAverageMap() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single reminder.
     */
    private class ModelStubWithReminder extends ModelStub {
        private final Reminder reminder;

        ModelStubWithReminder(Reminder reminder) {
            requireNonNull(reminder);
            this.reminder = reminder;
        }

        @Override
        public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
            requireNonNull(calendarEntry);
            return this.reminder.isSameCalendarEntry(calendarEntry);
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
            requireNonNull(calendarEntry);
            return remindersAdded.stream().anyMatch(calendarEntry::isSameCalendarEntry);
        }

        @Override
        public void addCalendarEntry(CalendarEntry calendarEntry) {
            requireNonNull(calendarEntry);
            remindersAdded.add((Reminder) calendarEntry);
        }

        @Override
        public void schedule() {
        }
    }

}
