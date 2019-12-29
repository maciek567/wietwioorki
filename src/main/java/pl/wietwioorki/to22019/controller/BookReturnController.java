package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.repository.ReservationRepository;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class BookReturnController extends AbstractWindowController implements Initializable {

    @Autowired
    ReservationRepository reservationRepository;

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
        Optional<Reservation> reservation = reservationRepository.findById(reservationID);

        if (reservation.isEmpty()) {
            System.out.println("Can't find reservation with id:" + reservationID);
            return;
        }
        reservation.get().returnBook();

        Book book = reservation.get().getBook();
        double tempSum = book.getAverageRating() * book.getVotesCount() + Double.parseDouble(msg.getText().substring(8));
        book.incrementVotesCount();
        book.setAverageRating(tempSum / book.getVotesCount());
        System.out.println("Book returned successfully");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        bookRating.setUpdateOnHover(true);
        msg.setText("Rating: 3.0");
        bookRating.ratingProperty().addListener((observable, oldValue, newValue) -> msg.setText("Rating: " + newValue));
    }
}
