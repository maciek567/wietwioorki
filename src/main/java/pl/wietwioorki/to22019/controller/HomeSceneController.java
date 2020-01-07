package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.util.AlertFactory;

import static pl.wietwioorki.to22019.util.InfoMessage.*;

@Controller
public class HomeSceneController extends AbstractWindowController {

    @FXML
    public Button enterLogin;

    @FXML
    public Button enterRegistration;

    @FXML
    public Button addReservationButton;

    @FXML
    public Button showReservationListButton;

    @FXML
    public Button showCompleteReservationListButton;

    @FXML
    public Button borrowBookButton;

    @FXML
    public Button returnBookButton;

    @FXML
    public Button showStatisticsButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button showBookListButton;

    @FXML
    private Button showFinesButton;

    @FXML
    public Text loggedInUser;

    @FXML
    public void handleNewBook(ActionEvent actionEvent) {
        System.out.println("New book view");
        openNewWindow("/layouts/AddBook.fxml");
    }

    @FXML
    public void handleShowBookList(ActionEvent actionEvent) {
        System.out.println("Show book list");
        openNewWindow("/layouts/BooksList.fxml");
    }

    @FXML
    public void handleEnterLogin(ActionEvent actionEvent) {
        if (sessionConstants.getUserLogin() == null) {
            System.out.println("login view");
            openNewWindow("/layouts/Login.fxml");
            if (sessionConstants.getUserLogin() != null) {
                loggedInUser.setText(sessionConstants.getUserLogin());
                enterLogin.setText("Logout");
            }
        } else {
            sessionConstants.logoutUser();
            loggedInUser.setText("guest");
            enterLogin.setText("Login");
            AlertFactory.showAlert(Alert.AlertType.INFORMATION, successfulLogout,
                    "You have successfully logout");
        }
    }

    @FXML
    public void handleEnterRegistration(ActionEvent actionEvent) {
        System.out.println("Registration view");
        openNewWindow("/layouts/Register.fxml");
    }

    @FXML
    public void handleNewReservation(ActionEvent actionEvent) {
        System.out.println("New reservation view");
        openNewWindow("/layouts/AddReservation.fxml");
    }

    @FXML
    public void handleShowReservationList(ActionEvent actionEvent) {
        System.out.println("Show reservation list");
        openNewWindow("/layouts/ReservationList.fxml");
    }

    @FXML
    public void handleShowCompleteReservationList(ActionEvent actionEvent) {
        System.out.println("Show complete reservation list");
        openNewWindow("/layouts/CompleteReservationList.fxml");
    }

    @FXML
    public void handleBorrowBook(ActionEvent actionEvent) {
        System.out.println("Show borrow book");
        openNewWindow("/layouts/BookBorrow.fxml");
    }

    @FXML
    public void handleReturnBook(ActionEvent actionEvent) {
        System.out.println("Show return book");
        openNewWindow("/layouts/BookReturn.fxml");
    }

    @FXML
    public void handleShowStatisticsButton(ActionEvent actionEvent) {
        System.out.println("Show return book");
        GenerateStatisticsController generateStatisticsController = new GenerateStatisticsController();
        openNewWindow("/layouts/Statistics.fxml");
    }

    @FXML
    public void handleShowFines(ActionEvent actionEvent) {
        if (!isCurrentUserGuest()) {
            System.out.println("Show fines");
            openNewWindow("/layouts/FineList.fxml");
        } else {
            AlertFactory.showAlert(Alert.AlertType.WARNING, loggedAsGuestHeader, loggedAsGuestContent);
        }
    }
}
