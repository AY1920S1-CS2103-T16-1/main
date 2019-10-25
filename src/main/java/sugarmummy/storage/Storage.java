package sugarmummy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.UniqueRecordList;
import sugarmummy.recmfood.model.UniqueFoodList;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserListStorage, UserPrefsStorage, CalendarStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    public Path getFoodListFilePath();

    public Optional<UniqueFoodList> readFoodList() throws DataConversionException, IOException;

    public Optional<UniqueFoodList> readFoodList(Path filePath) throws DataConversionException, IOException;

    public void saveFoodList(UniqueFoodList foodList) throws IOException;

    public void saveFoodList(UniqueFoodList foodList, Path filePath) throws IOException;

    public Path getRecordListFilePath();

    public Optional<UniqueRecordList> readRecordList() throws DataConversionException, IOException;

    public Optional<UniqueRecordList> readRecordList(Path filePath) throws DataConversionException, IOException;

    public void saveRecordList(UniqueRecordList recordList) throws IOException;

    public void saveRecordList(UniqueRecordList recordList, Path filePath) throws IOException;

    Path getEventFilePath();

    Path getReminderFilePath();

    Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException, IOException;

    void saveCalendar(ReadOnlyCalendar calendar) throws IOException;

    // ================ UserList methods ==============================
    @Override
    Path getUserListFilePath();

    @Override
    Optional<ReadOnlyUserList> readUserList() throws DataConversionException, IOException;

    @Override
    void saveUserList(ReadOnlyUserList userList) throws IOException;

    @Override
    List<Map<String, String>> getListOfFieldsContainingInvalidReferences();

}
