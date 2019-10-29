package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Record;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of records.
 */
public class RecordListPanel extends UiPart<Region> {
    private static final String FXML = "RecordListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecordListPanel.class);

    @FXML
    private ListView<Record> recordListView;

    public RecordListPanel(ObservableList<Record> recordList) {
        super(FXML);
        recordListView.setItems(recordList);
        recordListView.setCellFactory(listView -> new RecordListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Record} using a {@code BloodSugarRecordCard} or a
     * {@code BmiRecordCard}.
     */
    class RecordListViewCell extends ListCell<Record> {
        @Override
        protected void updateItem(Record record, boolean empty) {
            super.updateItem(record, empty);

            if (empty || record == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (record.getClass() == BloodSugar.class) {
                    setGraphic(new BloodSugarRecordCard((BloodSugar) record, getIndex() + 1).getRoot());
                } else if (record.getClass() == Bmi.class) {
                    setGraphic(new BmiRecordCard((Bmi) record, getIndex() + 1).getRoot());
                }
            }
        }
    }

}
