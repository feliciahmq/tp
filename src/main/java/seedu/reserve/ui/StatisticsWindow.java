package seedu.reserve.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    private BarChart<String, Number> barChart;

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
        configureBarChart();
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
        setBarChart(reservationStatistics);
        root.show();
    }

    /**
     * Configures pie chart settings.
     */
    private void configureBarChart() {
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        barChart.setTitle("Reservations by number of diners");
    }

    /**
     * Sets pie chart data from reservation statistics.
     *
     * @param reservationStatistics HashMap of categories to numerical values.
     */
    public void setBarChart(HashMap<String, Integer> reservationStatistics) {
        barChart.getData().clear();

        if (reservationStatistics == null || reservationStatistics.isEmpty()) {
            logger.info("No reservations found to display in chart.");
            statisticsMessage.setText("No reservations to summarize.");
            return;
        }

        statisticsMessage.setText("Summary of reservations");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Reservations");

        int maxCount = 1;
        for (int i = 1; i <= 10; i++) {
            String key = String.valueOf(i);
            int value = reservationStatistics.getOrDefault(key, 0);
            maxCount = Math.max(maxCount, value);
            series.getData().add(new XYChart.Data<>(key, value));
        }

        barChart.getData().add(series);

        if (series.getData().isEmpty()) {
            logger.warning("No data was added to the BarChart.");
        } else {
            barChart.getData().add(series);
            logger.fine("BarChart data set successfully.");
        }

        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(1);

        // Round up maxCount to the next nice value (e.g., +1 buffer or multiple of 5)
        int paddedUpperBound = (maxCount <= 10) ? 10 : ((maxCount + 4) / 5) * 5;
        yAxis.setUpperBound(paddedUpperBound);

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
