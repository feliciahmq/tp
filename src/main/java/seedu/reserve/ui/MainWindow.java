package seedu.reserve.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.reserve.commons.core.GuiSettings;
import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.logic.Logic;
import seedu.reserve.logic.commands.CommandResult;
import seedu.reserve.logic.commands.exceptions.CommandException;
import seedu.reserve.logic.parser.exceptions.ParseException;
import seedu.reserve.model.Model;
import seedu.reserve.model.reservation.Reservation;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Model model;

    // Independent Ui parts residing in this Ui container
    private ReservationListPanel reservationListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatisticsWindow statisticsWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane reservationListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.model = model;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        statisticsWindow = new StatisticsWindow();

        model.getFilteredReservationList().addListener((ListChangeListener<Reservation>) change -> {
            HashMap<String, Integer> updatedStats = model.getReservationStatistics();
            Platform.runLater(() -> {
                statisticsWindow.setBarChart(updatedStats);
            });
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
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
    void fillInnerParts() {
        reservationListPanel = new ReservationListPanel(logic.getFilteredReservationList());
        reservationListPanelPlaceholder.getChildren().add(reservationListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());


        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getReserveMateFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

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
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        Stage helpStage = helpWindow.getRoot();

        if (!helpStage.isShowing()) {
            helpWindow.show();
        } else {
            if (helpStage.isIconified()) {
                helpStage.setIconified(false);
            }
            helpWindow.focus();
        }

    }

    /**
     * Opens the statistics window or focuses on it if it's already opened.
     */
    @FXML
    public void handleStatistics() {
        Stage statsStage = statisticsWindow.getRoot();

        if (!statsStage.isShowing()) {
            statisticsWindow.show(logic.getReservationStatistics());
        } else {
            if (statsStage.isIconified()) {
                statsStage.setIconified(false);
            }
            statisticsWindow.focus();
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
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
        helpWindow.hide();
        statisticsWindow.hide();
    }

    public ReservationListPanel getReservationListPanel() {
        return reservationListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.reserve.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            Platform.runLater(() -> {
                statisticsWindow.setBarChart(logic.getReservationStatistics());
            });

            if (commandResult.isShowUserGuide()) {
                handleHelp();
            }

            if (commandResult.isShowStatistics()) {
                handleStatistics();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
