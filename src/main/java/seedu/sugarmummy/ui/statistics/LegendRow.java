package seedu.sugarmummy.ui.statistics;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seedu.sugarmummy.ui.UiPart;

/**
 * Represents a ui for a row in legend.
 */
public class LegendRow extends UiPart<Region> {

    private static final String FXML = "LegendRow.fxml";

    @FXML
    private Rectangle symbol;

    @FXML
    private Label lineChartDescription;

    public LegendRow(Color color, String legendDescription) {
        super(FXML);
        symbol.setFill(color);
        lineChartDescription.setText(legendDescription);
    }
}