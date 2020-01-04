package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.repository.ReservationRepository;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.BookBorrowValidator;

import static pl.wietwioorki.to22019.util.InfoMessage.*;

@Controller
public class BookBorrowController extends AbstractWindowController{

    @Autowired
    ReservationRepository reservationRepository;

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
        reservationRepository.save(reservation);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, bookSuccessfullyBorrowedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
