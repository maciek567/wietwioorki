package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.BookBorrowValidator;

import static pl.wietwioorki.to22019.util.InfoMessage.bookSuccessfullyBorrowedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

@Controller
public class BookBorrowController extends AbstractWindowController{

    @FXML
    public TextField pesel;

    @FXML
    public TextField reservationId;

    @FXML
    public Button addReservation;

    @FXML
    public void handleBorrowBook(ActionEvent actionEvent) {
        BookBorrowValidator bookBorrowValidator = sessionConstants.getBookBorrowValidator();

        if (!bookBorrowValidator.validatePesel(pesel.getText()) || !bookBorrowValidator.validateReservation(reservationId.getText())) {
            return;
        }

        Reservation reservation = bookBorrowValidator.getReservation();
        reservation.borrowBook();
        sessionConstants.getReservationRepository().save(reservation);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyBorrowedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
