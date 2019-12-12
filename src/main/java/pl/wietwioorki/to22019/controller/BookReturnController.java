package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class BookReturnController {

    @FXML
    public TextField pesel;

    @FXML
    public TextField returnedBookId;

    @FXML
    public Button returnBook;

    @FXML
    public void handleReturnBook(ActionEvent actionEvent) {

    }

}
