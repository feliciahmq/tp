package seedu.reserve.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;
    @FXML
    private StackPane placeHolder;

    public ResultDisplay() {
        super(FXML);
        configureTextArea();
    }

    private void configureTextArea() {
        // Enable text wrapping
        resultDisplay.setWrapText(true);

        // Make the TextArea grow with its parent
        resultDisplay.setPrefHeight(Region.USE_COMPUTED_SIZE);
        resultDisplay.setPrefWidth(Region.USE_COMPUTED_SIZE);

        // Bind the TextArea's dimensions to its container
        resultDisplay.prefWidthProperty().bind(placeHolder.widthProperty());
        resultDisplay.prefHeightProperty().bind(placeHolder.heightProperty());

        // Disable horizontal scrollbar (force wrapping)
        resultDisplay.setStyle("-fx-scrollbar-horizontal: hidden;");
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }
}