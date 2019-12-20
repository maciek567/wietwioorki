package pl.wietwioorki.to22019.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class AddAuthorController extends AbstractWindowController{

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public Button addAuthorButton;

    @FXML
    public DatePicker birthDate;

    @FXML
    public void handleAddNewAuthor(ActionEvent actionEvent) {
    }
}
