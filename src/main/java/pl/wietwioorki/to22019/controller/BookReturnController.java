package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.Rating;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.model.Reservation;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class BookReturnController extends AbstractWindowController implements Initializable {

    @FXML
    public TextField pesel;

    @FXML
    public TextField reservationId;

    @FXML
    private Rating bookRating = new Rating();

    @FXML
    private Label msg;

    @FXML
    public Button returnBook;

    @FXML
    public void handleReturnBook(ActionEvent actionEvent) {
        long reservationID;
        try {
            reservationID = Long.parseLong(reservationId.getText());
        } catch (NumberFormatException e) {
            System.out.println("Can't read reservation id :" + reservationId.getText());
            return;
        }
        Reservation reservation = ReservationDAO.findById(reservationID);

        if (reservation == null) {
            System.out.println("Can't find reservation with id:" + reservationID);
            return;
        }
        reservation.returnBook();
        System.out.println("Book returned successfully");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        bookRating.setUpdateOnHover(true);
        msg.setText("Rating: 3.0");
        bookRating.ratingProperty().addListener((observable, oldValue, newValue) -> msg.setText("Rating: " + newValue));
    }
}
