package seedu.sugarmummy.ui;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.ui.DisplayPaneType.BACKGROUND;
import static seedu.sugarmummy.ui.DisplayPaneType.BIO;
import static seedu.sugarmummy.ui.DisplayPaneType.COLOUR;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.commons.core.Messages;
import seedu.sugarmummy.logic.Logic;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.food.exception.FoodNotSuitableException;
import seedu.sugarmummy.ui.calendar.ReminderListPanel;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where other JavaFX elements
 * can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String MESSAGE_CANNOT_LOAD_WINDOW = "Unable to load window. :(";
    private static final String TEMPORARY_BACKGROUND_PATH = "/images/SpaceModified.jpg";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private StyleManager styleManager;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private MainDisplayPane mainDisplayPane;
    private ReminderListPanel reminderListPanel;

    @FXML
    private Scene scene;

    @FXML
    private VBox mainWindowPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane mainDisplayPanePlaceholder;

    @FXML
    private HBox resultDisplayPlaceholder;

    @FXML
    private StackPane reminderListPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        mainDisplayPane = new MainDisplayPane(logic);
        helpWindow = new HelpWindow();
        styleManager = new StyleManager(scene, mainWindowPlaceholder);
        setFontColour(logic.getGuiSettings());
        setBackground(logic.getGuiSettings());
        styleManager.setFontFamily("Futura");
    }

    /**
     * Constructor that displays an initial background image;
     */
    public MainWindow(Stage primaryStage, Logic logic, String imagePath) {
        this(primaryStage, logic);
        showInitialBackground(mainDisplayPanePlaceholder, imagePath);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Returns messages for invalid references in a given map.
     *
     * @param fieldsContainingInvalidReferences Map that contains field and invalid reference pairs.
     * @return Messages for invalid references in a given map.
     */
    private String getMessageForInvalidReferencesInMap(Map<String, String> fieldsContainingInvalidReferences) {
        StringBuilder sb = new StringBuilder();
        for (String fieldLabels : fieldsContainingInvalidReferences.keySet()) {
            String field = fieldLabels;
            String invalidReference = fieldsContainingInvalidReferences.get(field);
            sb.append("- ").append(invalidReference).append(" of field ").append(field).append("\n");
        }
        return sb.toString();
    }


    /**
     * Displays invalid references to the user if any.
     * Clears the list of invalid references after displaying to the user so the message would not be displayed again
     * during the next startup.
     *
     * @param resultDisplay ResultDisplay object that is used to display information to user.
     */
    private void displayInvalidReferences(ResultDisplay resultDisplay) {
        List<Map<String, String>> listOfFieldsContainingInvalidReferences = logic
                .getListOfFieldsContainingInvalidReferences();
        Map<String, String> guiFieldsContainingInvalidReferences =
                logic.getGuiSettings().getFieldsContainingInvalidReferences();

        StringBuilder sb = new StringBuilder();

        if (!listOfFieldsContainingInvalidReferences.isEmpty() || !guiFieldsContainingInvalidReferences.isEmpty()) {
            resultDisplay.appendNewLineInFeedBackToUser(2);
            sb.append(Messages.MESSAGE_UNABLE_TO_LOAD_REFERENCES);

            sb.append(getMessageForInvalidReferencesInMap(guiFieldsContainingInvalidReferences));

            for (Map<String, String> map : listOfFieldsContainingInvalidReferences) {
                sb.append(getMessageForInvalidReferencesInMap(map));
            }
        }
        resultDisplay.appendFeedbackToUser(sb.toString().trim());
        listOfFieldsContainingInvalidReferences.clear();
    }

    /**
     * Displays the welcome message to the user.
     *
     * @param resultDisplay ResultDisplay object that is used to display information to user.
     */
    private void displayWelcomeMessage(ResultDisplay resultDisplay) {
        if (!logic.getFilteredUserList().isEmpty()) {
            String name = logic.getFilteredUserList().get(0).getName().toString();
            resultDisplay.appendFeedbackToUser("Hi " + name + "! How are you feeling, and how can SugarMummy "
                    + "assist you today?");
        } else {
            resultDisplay.appendFeedbackToUser("Hello there! How are you feeling, and how can SugarMummy "
                    + "assist you today?\n" + Messages.MESSAGE_NO_BIO_FOUND);
        }
    }

    /**
     * Displays the main background to the user.
     *
     * @param mainDisplayPanePlaceholder MainDisplayPaneholder containing background to be displayed.
     * @imagePath String representation of path to background image to be displayed to the user upon startup.
     */
    private void showInitialBackground(StackPane mainDisplayPanePlaceholder, String imagePath) {
        mainDisplayPanePlaceholder.setStyle("-fx-background-image: url('" + imagePath + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-size: contain;");
    }

    /**
     * Fills up intiial placeholders of this window.
     *
     * @imagePath String representation of path to background image to be displayed to the user upon startup.
     */
    void fillInnerParts() throws URISyntaxException {
        resultDisplay = new ResultDisplay();
        displayWelcomeMessage(resultDisplay);
        displayInvalidReferences(resultDisplay);
        if (logic.getBackground().showDefaultBackground()) {
            resultDisplay.appendNewLineInFeedBackToUser(2);
            resultDisplay.appendFeedbackToUser(Messages.MESSAGE_TEMP_BACKGROUND_IMAGE_LOADED);
        }
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        reminderListPanel = new ReminderListPanel(logic.getPastReminderList());
        reminderListPlaceholder.getChildren().add(reminderListPanel.getRoot());
        logic.schedule();
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Sets the font colour based on {@code guiSettings}.
     *
     * @param guiSettings
     */
    private void setFontColour(GuiSettings guiSettings) {
        styleManager.setFontColour(guiSettings.getFontColour());
    }

    /**
     * Sets the background based on {@code guiSettings}.
     *
     * @param guiSettings
     */
    private void setBackground(GuiSettings guiSettings) {
        Background background = guiSettings.getBackground();
        if (background.showDefaultBackground()) {
            styleManager.setBackground(new Background("transparent"));
            mainWindowPlaceholder.setStyle("-fx-background-image: url('" + TEMPORARY_BACKGROUND_PATH + "'); "
                    + "-fx-background-position: center center; "
                    + "-fx-background-repeat: no-repeat;"
                    + "-fx-background-size: cover;");
            styleManager.setFontColour("yellow");
        } else {
            styleManager.setBackground(guiSettings.getBackground());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Returns the pane to be displayed depending on the type of pane to be displayed and whether the GUI has
     * been modified.
     * If the GUI has been modified, then the BIO page needs to reload to display the update if the current
     * pane displayed happens to be so.
     *
     * @param displayPaneType DisplayPaneType indicating whether the GUI is to be modified.
     * @param guiIsModified   Boolean indicating whether the GUI has been modified.
     * @return
     */
    private DisplayPaneType getPaneToDisplay(DisplayPaneType displayPaneType, boolean guiIsModified) {
        if (guiIsModified && mainDisplayPane.getCurrPaneType() == BIO) {
            return BIO;
        } else if (guiIsModified) {
            return null;
        } else {
            return displayPaneType;
        }
    }

    /**
     * Modifies the GUI based on the displayPaneType and returns true if the GUI has been modified
     *
     * @param displayPaneType DisplayPaneType indicating whether the GUI is to be modified.
     * @return Boolean indicating whether the GUI has been modified.
     */
    private boolean guiIsModified(DisplayPaneType displayPaneType) {
        if (displayPaneType == COLOUR) {
            setFontColour(logic.getGuiSettings());
            return true;
        } else if (displayPaneType == BACKGROUND) {
            setBackground(logic.getGuiSettings());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Switches the main display pane to the specified UI part.
     */
    public void switchToMainDisplayPane(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated) {
        if (!Arrays.asList(DisplayPaneType.values()).contains(displayPaneType)) {
            throw new NullPointerException();
        } else if (displayPaneType != mainDisplayPane.getCurrPaneType() || newPaneIsToBeCreated) {
            DisplayPaneType paneToDisplay = getPaneToDisplay(displayPaneType, guiIsModified(displayPaneType));
            if (paneToDisplay == null) {
                return;
            }
            newPaneIsToBeCreated = ((displayPaneType == COLOUR || displayPaneType == BACKGROUND)
                    && paneToDisplay == BIO) || newPaneIsToBeCreated;
            mainDisplayPanePlaceholder.setStyle(null);
            mainDisplayPanePlaceholder.getChildren().clear();
            mainDisplayPanePlaceholder.getChildren()
                    .add(requireNonNull(mainDisplayPane.get(paneToDisplay, newPaneIsToBeCreated).getRoot()));
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), logic.getFontColour(), logic.getBackground());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
        styleManager.resetStyleSheets();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {

            CommandResult commandResult = logic.execute(commandText);

            logger.info("Result: " + commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
                return commandResult;
            }

            if (commandResult.isExit()) {
                handleExit();
                return commandResult;

            } else {
                try {
                    switchToMainDisplayPane(logic.getDisplayPaneType(), logic.getnewPaneIsToBeCreated());
                    logger.info("Result: " + commandResult.getFeedbackToUser());
                    resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                } catch (NullPointerException e) {
                    String feedbackToUser = commandResult.getFeedbackToUser() + "\n" + MESSAGE_CANNOT_LOAD_WINDOW;
                    resultDisplay.setFeedbackToUser(feedbackToUser);
                    return new CommandResult(feedbackToUser);
                }
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (FoodNotSuitableException e) {
            logger.info("Not suitable food input: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}