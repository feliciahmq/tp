package seedu.reserve.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.reserve.model.reservation.Reservation;

/**
 * An UI component that displays information of a {@code Reservation}.
 */
public class ReservationCard extends UiPart<Region> {

    private static final String FXML = "ReservationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/ReserveMate-level4/issues/336">The issue on ReserveMate level 4</a>
     */

    public final Reservation reservation;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label diners;
    @FXML
    private Label dateTime;

    /**
     * Creates a {@code ReservationCode} with the given {@code Reservation} and index to display.
     */
    public ReservationCard(Reservation reservation, int displayedIndex) {
        super(FXML);
        this.reservation = reservation;
        id.setText(displayedIndex + ". ");
        name.setText(reservation.getName().fullName);
        diners.setText(reservation.getDiners().value + " Diners");
        dateTime.setText(reservation.getDateTime().toString());
    }
}
