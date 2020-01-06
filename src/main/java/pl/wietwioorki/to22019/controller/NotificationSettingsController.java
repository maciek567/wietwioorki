package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationSettingsController extends AbstractWindowController {

    @FXML
    public CheckBox readyBook;

    @FXML
    public CheckBox overdueBook;

    @FXML
    public CheckBox newReservation;

    @FXML
    public CheckBox borrowedBook;

    @FXML
    public CheckBox returnedBook;

    @FXML
    public void handleReadyBook(ActionEvent actionEvent) {
        sessionConstants.getCurrentUser().changeReadyBook();
        sessionConstants.getUserRepository().save(sessionConstants.getCurrentUser());
    }

    @FXML
    public void handleOverdueBook(ActionEvent actionEvent) {
        sessionConstants.getCurrentUser().changeOverdueBook();
        sessionConstants.getUserRepository().save(sessionConstants.getCurrentUser());
    }

    @FXML
    public void handleNewReservation(ActionEvent actionEvent) {
        sessionConstants.getCurrentUser().changeNewReservation();
        sessionConstants.getUserRepository().save(sessionConstants.getCurrentUser());
    }

    @FXML
    public void handleBorrowedBook(ActionEvent actionEvent) {
        sessionConstants.getCurrentUser().changeBorrowedBook();
        sessionConstants.getUserRepository().save(sessionConstants.getCurrentUser());
    }

    @FXML
    public void handleReturnedBook(ActionEvent actionEvent) {
        sessionConstants.getCurrentUser().changeReturnedBook();
        sessionConstants.getUserRepository().save(sessionConstants.getCurrentUser());
    }
}
