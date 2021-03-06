package seedu.sugarmummy.ui.records;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.ui.UiPart;

/**
 * An UI component that displays information of a {@code BloodSugarRecord}.
 */
public class BloodSugarRecordCard extends UiPart<Region> {

    private static final String FXML = "BloodSugarRecordListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a consequence, UI
     * elements' variable names cannot be set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Record record;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label recordType;
    @FXML
    private Label dateTime;
    @FXML
    private Label concentration;


    public BloodSugarRecordCard(BloodSugar record, int displayedIndex) {
        super(FXML);
        this.record = record;
        id.setText(displayedIndex + ". ");
        String recordType = record.getClass().getSimpleName();
        this.recordType.setText(recordType);
        dateTime.setText("DateTime: " + record.getDateTime().toString());
        concentration.setText("Concentration: " + record.getConcentration().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BloodSugarRecordCard)) {
            return false;
        }

        // state check
        BloodSugarRecordCard card = (BloodSugarRecordCard) other;
        return id.getText().equals(card.id.getText())
                && record.equals(card.record);
    }
}
