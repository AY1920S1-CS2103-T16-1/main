package seedu.sugarmummy.ui.statistics;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.ui.UiPart;

/**
 * Represents a panel of an average graph and its legend.
 */
public class AverageGraphPanel extends UiPart<Region> {
    private static final String FXML = "AverageGraph.fxml";

    private final AverageGraph averageGraph;

    private final LegendBox legendBox;

    private final Logger logger = LogsCenter.getLogger(AverageGraphPanel.class);

    @FXML
    private ScrollPane lineChartScrollPane;

    @FXML
    private VBox vBox;

    public AverageGraphPanel(ObservableMap<LocalDate, Double> averageMap, SimpleStringProperty averageType,
                             SimpleStringProperty recordType) {
        super(FXML);

        this.averageGraph = new AverageGraph(averageMap, averageType, recordType);
        this.legendBox = new LegendBox(recordType);

        vBox.getChildren().add(averageGraph.getAverageGraph());
        vBox.getChildren().add(legendBox.getRoot());
        lineChartScrollPane.setContent(vBox);
    }

}
