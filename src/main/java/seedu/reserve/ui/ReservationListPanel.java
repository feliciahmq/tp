package seedu.reserve.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.reserve.commons.core.LogsCenter;
import seedu.reserve.model.reservation.Reservation;

/**
 * Panel containing the list of reservations.
 */
public class ReservationListPanel extends UiPart<Region> {
    private static final String FXML = "ReservationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ReservationListPanel.class);

    @FXML
    private ListView<Reservation> reservationListView;

    /**
     * Creates a {@code ReservationListPanel} with the given {@code ObservableList}.
     */
    public ReservationListPanel(ObservableList<Reservation> reservationList) {
        super(FXML);
        reservationListView.setItems(reservationList);
        reservationListView.setCellFactory(listView -> new ReservationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Reservation} using a {@code ReservationCard}.
     */
    class ReservationListViewCell extends ListCell<Reservation> {
        @Override
        protected void updateItem(Reservation reservation, boolean empty) {
            super.updateItem(reservation, empty);

            if (empty || reservation == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ReservationCard(reservation, getIndex() + 1).getRoot());
            }
        }
    }

}
