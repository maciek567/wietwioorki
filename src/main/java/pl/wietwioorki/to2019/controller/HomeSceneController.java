package pl.wietwioorki.to2019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Controller;

@Controller
public class HomeSceneController {
    @FXML
    private Button reader;

    @FXML
    private Button book;

    @FXML
    public void handleNewReader(ActionEvent actionEvent) {
        System.out.println("New reader added");
    }

    @FXML
    public void handleNewBook(ActionEvent actionEvent) {
        System.out.println("New book added");
    }
}
