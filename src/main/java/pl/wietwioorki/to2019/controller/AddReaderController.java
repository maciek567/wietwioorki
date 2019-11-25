package pl.wietwioorki.to2019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class AddReaderController {

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public Button add_reader;

    @FXML
    public void handleAddNewReader(ActionEvent actionEvent) {
        System.out.println("Added new reader!");
    }
}
