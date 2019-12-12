package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Controller;

@Controller
public class HomeSceneController extends AbstractWindowControler{

    @FXML
    public Button enterLogin;

    @FXML
    public Button enterRegistration;

    @FXML
    public Button addAuthorButton;

    @FXML
    public Button addReservationButton;

    @FXML
    public Button showReservationListButton;

    @FXML
    private Button addReaderButton;

    @FXML
    private Button addBookButton;

    @FXML
    private Button showBookListButton;

    @FXML
    public void handleNewReader(ActionEvent actionEvent) {
        System.out.println("New reader view");
        openNewWindow("/layouts/AddReader.fxml");
    }

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
    }

    @FXML
    public void handleEnterRegistration(ActionEvent actionEvent) {
        System.out.println("Registration view");
        openNewWindow("/layouts/Register.fxml");
    }

    @FXML
    public void handleNewAuthor(ActionEvent actionEvent) {
        System.out.println("New author view");
        openNewWindow("/layouts/AddAuthor.fxml");
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
}
