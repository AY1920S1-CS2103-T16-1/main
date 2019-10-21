package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URISyntaxException;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where other JavaFX elements
 * can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String MESSAGE_CANNOT_LOAD_WINDOW = "Unable to load window. :(";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private ThemeManager themeManager;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private MainDisplayPane mainDisplayPane;

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
    private StackPane resultDisplayPlaceholder;


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
        themeManager = new ThemeManager(scene);
        setFontColour(logic.getGuiSettings());

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
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts(String imagePath) throws URISyntaxException {

        //        ImageView imageView = new ImageView(imagePath);
        //        imageView.fitWidthProperty().bind(mainDisplayPanePlaceholder.widthProperty());
        //        imageView.fitHeightProperty().bind(mainDisplayPanePlaceholder.heightProperty());
        //        imageView.setPreserveRatio(true);
        //        mainDisplayPanePlaceholder.getChildren().add(imageView);

        mainDisplayPanePlaceholder.setStyle("-fx-background-image: url('" + imagePath + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: no-repeat;"
                + "-fx-background-size: contain;");

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

//        try {
//            themeManager.setFontColour("blue");
//        } catch (Exception e) {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            resultDisplay.setFeedbackToUser(sw.toString());
//            InputStream is = this.getClass().getResourceAsStream("/view/DarkTheme.css");
//            try {
//                resultDisplay.setFeedbackToUser(new String(is.readAllBytes(), StandardCharsets.UTF_8));
//            } catch (IOException ex) {
//                resultDisplay.setFeedbackToUser("IO Exception" + ex);
//            }
//        }

//        ObservableList<String> styleSheets = scene.getStylesheets();
//        String darkThemeUri = styleSheets.get(0);
//        try {
//            Path darkThemePath = Paths.get(new URI(darkThemeUri));
//            File darkTheme = new File(darkThemePath.toString());
//            String myThemePathString = darkThemePath.getParent().toString() + "/MyTheme.css";
//            File myTheme = new File(myThemePathString);
//            System.out.println("myThemeExists" + myTheme.exists());
//            styleSheets.set(0, myTheme.toURI().toString());
//            System.out.println(styleSheets);
//            System.out.println(darkThemeUri);
//            System.out.println(darkTheme.exists());
//            BufferedReader br = new BufferedReader(new FileReader(myTheme));
//            String s;
//            while ((s = br.readLine()) != null) {
//                System.out.println(s);
//            }
//        } catch (URISyntaxException | IOException e) {
//            System.out.println(e);
//        }

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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
     * @param guiSettings
     */
    private void setFontColour(GuiSettings guiSettings) {
        themeManager.setFontColour(guiSettings.getFontColour());
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
     * Switches the main display pane to the specified UI part.
     */
    public void switchToMainDisplayPane(DisplayPaneType displayPaneType, boolean newPaneIsToBeCreated) {
        if (displayPaneType == DisplayPaneType.COLOUR) {
            setFontColour(logic.getGuiSettings());
        } else if (!displayPaneType.equals(mainDisplayPane.getCurrPaneType()) || newPaneIsToBeCreated) {
            mainDisplayPanePlaceholder.setBackground(Background.EMPTY);
            mainDisplayPanePlaceholder.getChildren().clear();
            mainDisplayPanePlaceholder.getChildren()
                .add(requireNonNull(mainDisplayPane.get(displayPaneType, newPaneIsToBeCreated).getRoot()));
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
            (int) primaryStage.getX(), (int) primaryStage.getY(), logic.getFontColour());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
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
        }
    }
}
