package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class AddBookController {

    @FXML
    public TextField bookTittle;

    @FXML
    public TextField authorName;

    @FXML
    public TextField publicationDate;

    @FXML
    public TextField genre;

    @FXML
    public Button addBook;

    @FXML
    public void handleAddNewBook(ActionEvent actionEvent) {
        System.out.println("Added new book");
    }
}
