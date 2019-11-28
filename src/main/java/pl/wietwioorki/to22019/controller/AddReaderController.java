package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import pl.wietwioorki.to22019.model.Reader;

import java.util.Date;

@Controller
public class AddReaderController {

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public TextField pesel;

    @FXML
    public TextField birthDate;

    @FXML
    public Button addReader;



    @FXML
    public void handleAddNewReader(ActionEvent actionEvent) {
        System.out.println("Added new reader");
        Reader reader = new Reader(Integer.parseInt(pesel.getText()), name + " " + surname, new Date());

    }
}
