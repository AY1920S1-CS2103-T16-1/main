package seedu.sugarmummy.ui.achievements;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.sugarmummy.ui.UiPart;

/**
 * A ui for a single ImageView used to contain a tile representing the user's achievements
 */
public class AchievementsImageView extends UiPart<Region> {

    private static final String FXML = "AchievementsImageView.fxml";

    @FXML
    private ImageView achievementsImageView;

    public AchievementsImageView() {
        super(FXML);
    }

    public ImageView getImageView() {
        return achievementsImageView;
    }
}