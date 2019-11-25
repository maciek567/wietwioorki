package pl.wietwioorki.to2019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class AddBookController {

    @FXML
    public TextField book_tittle;

    @FXML
    public TextField authors_name;

    @FXML
    public TextField release_date;

    @FXML
    public Button add_book;

    @FXML
    public void handleAddNewBook(ActionEvent actionEvent) {
        System.out.println("Added new book");
    }
}
