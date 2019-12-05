package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Controller;

@Controller
public class HomeSceneController extends AbstractWindowControler{

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
}
