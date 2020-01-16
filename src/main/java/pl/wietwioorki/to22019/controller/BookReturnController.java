package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.control.Rating;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.CompleteReservation;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.util.AlertFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static pl.wietwioorki.to22019.util.ErrorMessage.cannotReturnBookErrorContent;
import static pl.wietwioorki.to22019.util.ErrorMessage.generalErrorHeader;
import static pl.wietwioorki.to22019.util.InfoMessage.bookSuccessfullyReturnedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

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
        // to remove - beginning
        long reservationID;
        try {
            reservationID = Long.parseLong(reservationId.getText());
        } catch (NumberFormatException e) {
            System.out.println("Can't read reservation id :" + reservationId.getText());
            return;
        }
        Optional<Reservation> reservation = sessionConstants.getReservationRepository().findById(reservationID);

        if (reservation.isEmpty()) {
            System.out.println("Can't find reservation with id:" + reservationID);
            return;
        }
        // to remove - end

        // FIXME: @up: to remove. Only checking if book is not already returned (@down)

        if (!reservation.get().getReservationStatus().equals(ReservationStatus.ACTIVE) &&
                !reservation.get().getReservationStatus().equals(ReservationStatus.OVERDUE)) {
            System.out.println("RESERVATION STATUS: " + reservation.get().getReservationStatus());
            AlertFactory.showAlert(Alert.AlertType.ERROR, generalErrorHeader + "returning book", cannotReturnBookErrorContent);
            return;
        }

        reservation.get().returnBook();

        Book book = reservation.get().getBook();
        double tempSum = book.getAverageRating() * book.getVotesCount() + Double.parseDouble(msg.getText().substring(8));
        book.incrementVotesCount();
        book.setAverageRating(tempSum / book.getVotesCount());

        sessionConstants.getBookRepository().save(book);

        sessionConstants.getReservationRepository().delete(reservation.get());

        Reservation oldReservation = reservation.get();
        sessionConstants.getCompleteReservationRepository().save(new CompleteReservation(oldReservation.getReader(), oldReservation.getBook(),
                oldReservation.getReservationStartDate(), oldReservation.getReservationStatus().equals(ReservationStatus.OVERDUE)));

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyReturnedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        msg.setText("Rating: 2.0");
        bookRating.ratingProperty().addListener((observable, oldValue, newValue) -> msg.setText("Rating: " + newValue));
    }
}