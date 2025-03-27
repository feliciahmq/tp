package seedu.reserve.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.reserve.commons.core.LogsCenter;

/**
 * The Statistics Window. Provides the basic application layout containing
 * the statistics.
 */
public class StatisticsWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL =
            "https://github.com/AY2425S2-CS2103-F08-1/tp/blob/master/docs/UserGuide.md";
    public static final String STATISTICS_MESSAGE = "Statistics of reservations";

    private static final Logger logger = LogsCenter.getLogger(StatisticsWindow.class);
    private static final String FXML = "StatisticsWindow.fxml";

    @FXML
    private Label statisticsMessage;

    @FXML
    private PieChart pieChart;

    @FXML
    private VBox messageContainer;


    /**
     * Creates a new Statistics Window.
     *
     * @param root Stage to use as the root of the Statistics Window.
     */
    public StatisticsWindow(Stage root) {
        super(FXML, root);
        statisticsMessage.setText(STATISTICS_MESSAGE);
        configurePieChart();
    }

    /**
     * Creates a new StatisticsWindow.
     */
    public StatisticsWindow() {
        this(new Stage());
    }

    /**
     * Shows the statistics window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show(HashMap<String, Integer> reservationStatistics) {
        logger.fine("Showing statistics page of reservations.");
        Stage root = getRoot();
        setPieChart(reservationStatistics);
        root.show();
    }

    /**
     * Configures pie chart settings.
     */
    private void configurePieChart() {
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);
        pieChart.setTitle("Reservations by number of diners");
    }

    /**
     * Sets pie chart data from reservation statistics.
     *
     * @param reservationStatistics HashMap of categories to numerical values.
     */
    public void setPieChart(HashMap<String, Integer> reservationStatistics) {
        pieChart.getData().clear();

        for (String numOfDiners : reservationStatistics.keySet()) {
            Integer value = reservationStatistics.get(numOfDiners);

            if (value != null && value > 0) {
                PieChart.Data slice = new PieChart.Data(numOfDiners, value);
                pieChart.getData().add(slice);
            }
        }

        if (pieChart.getData().isEmpty()) {
            logger.warning("No data was added to the PieChart.");
        } else {
            logger.fine("PieChart data set successfully.");
        }

    }

    /**
     * Returns true if the statistics window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the statistics window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the statistics window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
