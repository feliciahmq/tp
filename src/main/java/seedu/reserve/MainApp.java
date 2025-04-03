package seedu.reserve;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.reserve.commons.core.Config;
import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.commons.core.Version;
import seedu.reserve.commons.exceptions.DataLoadingException;
import seedu.reserve.commons.util.ConfigUtil;
import seedu.reserve.commons.util.StringUtil;
import seedu.reserve.logic.Logic;
import seedu.reserve.logic.LogicManager;
import seedu.reserve.model.Model;
import seedu.reserve.model.ModelManager;
import seedu.reserve.model.ReadOnlyReserveMate;
import seedu.reserve.model.ReadOnlyUserPrefs;
import seedu.reserve.model.ReserveMate;
import seedu.reserve.model.UserPrefs;
import seedu.reserve.model.util.SampleDataUtil;
import seedu.reserve.storage.JsonReserveMateStorage;
import seedu.reserve.storage.JsonUserPrefsStorage;
import seedu.reserve.storage.ReserveMateStorage;
import seedu.reserve.storage.Storage;
import seedu.reserve.storage.StorageManager;
import seedu.reserve.storage.UserPrefsStorage;
import seedu.reserve.ui.Ui;
import seedu.reserve.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ReserveMate ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ReserveMateStorage reserveMateStorage = new JsonReserveMateStorage(userPrefs.getReserveMateFilePath());
        storage = new StorageManager(reserveMateStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, model);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s reservation book and
     * {@code userPrefs}. <br>
     * The data from the sample reservation book will be used instead if {@code storage}'s
     * reservation book is not found,
     * or an empty reservation book will be used instead if errors occur when reading
     * {@code storage}'s reservation book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getReserveMateFilePath());

        Optional<ReadOnlyReserveMate> reserveMateOptional;
        ReadOnlyReserveMate initialData;
        try {
            reserveMateOptional = storage.readReserveMate();
            if (!reserveMateOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getReserveMateFilePath()
                        + " populated with a sample ReserveMate.");
            }
            initialData = reserveMateOptional.orElseGet(SampleDataUtil::getSampleReserveMate);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getReserveMateFilePath() + " could not be loaded."
                    + " Will be starting with an empty ReserveMate.");
            initialData = new ReserveMate();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ReserveMate " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ReserveMate ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
