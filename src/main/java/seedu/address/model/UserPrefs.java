package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.aesthetics.Colour;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");
    private Path userListFilePath = Paths.get("data", "userList.json");
    private Path foodListFilePath = Paths.get("data", "foodlist.json");
    private Path recordListFilePath = Paths.get("data", "recordlist.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    //=========== User List =============================================================

    public Path getUserListFilePath() {
        return userListFilePath;
    }

    public void setUserListFilePath(Path userListFilePath) {
        requireNonNull(userListFilePath);
        this.userListFilePath = userListFilePath;
    }

    //=========== Aesthetics =============================================================

    @Override
    public Colour getFontColour() {
        return new Colour(this.guiSettings.getFontColour());
    }

    public void setFontColour(Colour fontColour) {
        requireNonNull(fontColour);
        this.guiSettings.setFontColour(fontColour);
    }

    //=========== Food Map =============================================================


    public Path getFoodListFilePath() {
        return foodListFilePath;
    }

    //=========== Records =============================================================

    public Path getRecordListFilePath() {
        return recordListFilePath;
    }

    public void setFoodListFilePath(Path foodListFilePath) {
        this.foodListFilePath = foodListFilePath;
    }

    public void setRecordListFilePath(Path recordListFilePath) {
        this.recordListFilePath = recordListFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
