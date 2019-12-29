package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.repository.ReservationRepository;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.ReservationValidator;

import static pl.wietwioorki.to22019.util.InfoMessage.reservationSuccessfullyCreatedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

@Controller
public class AddReservationController extends AbstractWindowController {

    @Autowired
    ReservationRepository reservationRepository;

    @FXML
    public TextField pesel;

    @FXML
    public TextField bookTitle;

    @FXML
    public Button addReservation;

    @FXML
    public void handleAddReservation(ActionEvent actionEvent) {
        System.out.println("Adding new reservation");

        //todo: check if book is not already booked or borrowed!

        ReservationValidator reservationValidator = constants.getReservationValidator();
        if (!reservationValidator.validatePesel(pesel.getText()) ||
                !reservationValidator.validateReader(pesel.getText()) ||
                !reservationValidator.validateBook(bookTitle.getText())) {
            return;
        }

        Book reservedBook = constants.getReservedBook(bookTitle.getText());
        ReservationStatus reservationStatus = reservedBook.getReaderQueueSize() == 0 ? ReservationStatus.READY : ReservationStatus.PENDING;

        // todo: validate whole reservation
        Reservation reservation = new Reservation(reservationValidator.getReader().get(), reservedBook, null, null, reservationStatus);

        reservedBook.pushReaderToQueue(reservationValidator.getReader().get());
        reservationRepository.save(reservation);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, reservationSuccessfullyCreatedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
