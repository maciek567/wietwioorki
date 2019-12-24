package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.dao.ReservationDAO;
import pl.wietwioorki.to22019.dao.generator.DataGenerator;
import pl.wietwioorki.to22019.model.Book;
import pl.wietwioorki.to22019.model.Reservation;
import pl.wietwioorki.to22019.model.ReservationStatus;
import pl.wietwioorki.to22019.util.AlertFactory;
import pl.wietwioorki.to22019.validator.ReservationValidator;

import static pl.wietwioorki.to22019.util.InfoMessage.reservationSuccessfullyCreatedContent;
import static pl.wietwioorki.to22019.util.InfoMessage.successHeader;

@Controller
public class AddReservationController extends AbstractWindowController {

    @FXML
    public TextField pesel;

    @FXML
    public TextField bookTitle;

    @FXML
    public Button addReservation;

    @FXML
    public void handleAddReservation(ActionEvent actionEvent) {
        System.out.println("Adding new reservation");

        ReservationValidator reservationValidator = new ReservationValidator();
        if (!reservationValidator.validatePesel(pesel.getText()) ||
                !reservationValidator.validateReader(pesel.getText()) ||
                !reservationValidator.validateBook(bookTitle.getText())) {
            return;
        }

        Book reservedBook = constants.getReservedBook(bookTitle.getText());
        ReservationStatus reservationStatus = reservedBook.getReaderQueueSize() == 0 ? ReservationStatus.READY : ReservationStatus.PENDING;

        Reservation reservation = new Reservation(DataGenerator.generateId(), reservationValidator.getReader(), reservedBook, null, null, reservationStatus);

        reservedBook.pushReaderToQueue(reservationValidator.getReader());
        ReservationDAO.addReservation(reservation);

        AlertFactory.showAlert(Alert.AlertType.INFORMATION, successHeader, reservationSuccessfullyCreatedContent);
        closeWindowAfterSuccessfulAction(actionEvent);
    }
}
