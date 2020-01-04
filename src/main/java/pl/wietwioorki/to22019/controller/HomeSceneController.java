package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

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
    private Button addBookButton;

    @FXML
    private Button showBookListButton;

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
        System.out.println("login view");
        openNewWindow("/layouts/Login.fxml");
        loggedInUser.setText(String.valueOf(sessionConstants.getUserLogin()));
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

    public void handleShowReservationList(ActionEvent actionEvent) {
        System.out.println("Show reservation list");
        openNewWindow("/layouts/ReservationList.fxml");
    }

    public void handleShowCompleteReservationList(ActionEvent actionEvent) {
        System.out.println("Show complete reservation list");
        openNewWindow("/layouts/CompleteReservationList.fxml");
    }

    public void handleBorrowBook(ActionEvent actionEvent) {
        System.out.println("Show borrow book");
        openNewWindow("/layouts/BookBorrow.fxml");
    }

    public void handleReturnBook(ActionEvent actionEvent) {
        System.out.println("Show return book");
        openNewWindow("/layouts/BookReturn.fxml");
    }
}
