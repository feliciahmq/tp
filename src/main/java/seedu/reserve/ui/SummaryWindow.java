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
 * The Summary Window. Provides the basic application layout containing
 * a summary statistics.
 */
public class SummaryWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL =
            "https://github.com/AY2425S2-CS2103-F08-1/tp/blob/master/docs/UserGuide.md";
    public static final String SUMMARY_MESSAGE = "Summary of reservations";

    private static final Logger logger = LogsCenter.getLogger(SummaryWindow.class);
    private static final String FXML = "SummaryWindow.fxml";

    @FXML
    private Label summaryMessage;

    @FXML
    private PieChart pieChart;

    @FXML
    private VBox messageContainer;


    /**
     * Creates a new SummaryWindow.
     *
     * @param root Stage to use as the root of the SummaryWindow.
     */
    public SummaryWindow(Stage root) {
        super(FXML, root);
        summaryMessage.setText(SUMMARY_MESSAGE);
        configurePieChart();
    }

    /**
     * Creates a new SummaryWindow.
     */
    public SummaryWindow() {
        this(new Stage());
    }

    /**
     * Shows the summary window.
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
    public void show(HashMap<String, Integer> reservationSummary) {
        logger.fine("Showing summary page of reservations.");
        Stage root = getRoot();
        setPieChart(reservationSummary);
        root.show();
    }

    /**
     * Configures pie chart settings.
     */
    private void configurePieChart() {
        pieChart.setLegendVisible(true);
        pieChart.setLabelsVisible(true);
        pieChart.setTitle("Reservations by categories");
    }

    /**
     * Sets pie chart data from reservation summary.
     *
     * @param reservationSummary HashMap of categories to numerical values.
     */
    public void setPieChart(HashMap<String, Integer> reservationSummary) {
        pieChart.getData().clear();

        for (String category : reservationSummary.keySet()) {
            Integer value = reservationSummary.get(category);
            if (value != null && value > 0) {
                PieChart.Data slice = new PieChart.Data(category, value);
                pieChart.getData().add(slice);
            }
        }
    }

    /**
     * Returns true if the summary window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the summary window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the summary window.
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
